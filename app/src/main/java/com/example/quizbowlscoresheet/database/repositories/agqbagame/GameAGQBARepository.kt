package com.example.quizbowlscoresheet.database.repositories.agqbagame

import androidx.annotation.WorkerThread
import androidx.room.withTransaction
import com.example.quizbowlscoresheet.database.models.*
import kotlinx.coroutines.flow.Flow

class GameAGQBARepository(
    private val database: GameAGQBADatabase
) {
    private val gameDao = database.gameDao()
    private val tossupDao = database.tossupDao()
    private val teamDao = database.teamDao()
    private val bonusDao = database.bonusDao()
    private val lightningDao = database.lightningDao()

    @WorkerThread
    suspend fun newGameAGQBA(): Long = database.withTransaction {
        // TODO: have real team input
        val team1 = teamDao.insertTeam(Team(null, "team 1!"))
        val team2 = teamDao.insertTeam(Team(null, "team 2!"))
        val gameId = gameDao.insertGame(Game(null, team1, team2, 0, 0))
        val tossups = blankAGQBATossups(gameId)
        tossupDao.insertTossupList(tossups)
        val bonusCategories = blankBonusCategories(gameId)
        val categoryIds = bonusDao.insertBonusCategoryInfoList(bonusCategories)
        val bonusQuestions = categoryIds.flatMap { blankBonusQuestions(it) }
        bonusDao.insertBonusQuestions(bonusQuestions)
        val lightningRounds = listOf(
            blankLightningCategory(gameId, TeamAnswered.TEAM1),
            blankLightningCategory(gameId, TeamAnswered.TEAM2)
        )
        val lightningIds = lightningDao.insertLightningCategoryInfoList(lightningRounds)
        val lightningQuestions = lightningIds.flatMap { blankLightningQuestions(it) }
        lightningDao.insertLightningQuestionList(lightningQuestions)
        return@withTransaction gameId
    }

    fun getAllGamesFlow(): Flow<List<Game>> = gameDao.getGameList()

    fun getGameWithTeamListFlow(): Flow<List<GameWithTeams>> = gameDao.getGameWithTeamList()

    fun getGameAGQBAById(id: Long) = gameDao.getGameAGQBAFlowById(id)

    fun getRoundNTossups(game: Game, roundNumber: Int) = tossupDao.getRoundNTossups(game.id!!, roundNumber)

    fun getBonusCategoryByNumber(gameId: Long, categoryNumber: Int) = bonusDao.getBonusCategoryByNumber(gameId, categoryNumber)

    fun getLightningCategoryByTeam(gameId: Long, team: TeamAnswered) = lightningDao.getLightningCategoryByTeam(gameId, team)

    suspend fun updateTeam(team: Team) {
        teamDao.updateTeam(team)
    }

    suspend fun updateTossup(tossup: Tossup) {
        tossupDao.updateTossup(tossup)
        val gameAGQBA = gameDao.getGameById(tossup.gameId)
        updateGameScores(gameAGQBA)
    }

    suspend fun updateRound2Tossup(tossup: Tossup) = database.withTransaction {
        tossupDao.updateTossup(tossup)
        val gameAGQBA = gameDao.getGameById(tossup.gameId)
        val answeredTossupTeams = gameAGQBA.round2Tossups
            .filter { it.team != TeamAnswered.NONE }
            .map { it.team }
        val defaults = List(gameAGQBA.bonusCategories.size - answeredTossupTeams.size) { TeamAnswered.NONE }
        val teamOrder = answeredTossupTeams.plus(defaults)
        val teamsToCategories = teamOrder.zip(gameAGQBA.bonusCategories.map { it.categoryInfo })
        for ((newTeam, category) in teamsToCategories) {
            bonusDao.updateBonusCategoryInfo(category.copy(team = newTeam))
        }
        val updatedGame = gameDao.getGameById(tossup.gameId)
        updateGameScores(updatedGame)
    }

    suspend fun updateBonusQuestion(bonusQuestion: BonusQuestion) {
        bonusDao.updateBonusQuestion(bonusQuestion)
        val category = bonusDao.getBonusCategoryInfoById(bonusQuestion.categoryId)
        val gameAGQBA = gameDao.getGameById(category.gameId)
        updateGameScores(gameAGQBA)
    }

    suspend fun updateBonusCategoryInfo(bonusCategoryInfo: BonusCategoryInfo) {
        bonusDao.updateBonusCategoryInfo(bonusCategoryInfo)
        val gameAGQBA = gameDao.getGameById(bonusCategoryInfo.gameId)
        updateGameScores(gameAGQBA)
    }

    suspend fun updateLightningQuestion(lightningQuestion: LightningQuestion) {
        lightningDao.updateLightningQuestion(lightningQuestion)
        val category = lightningDao.getLightningCategoryInfoById(lightningQuestion.categoryId)
        val gameAGQBA = gameDao.getGameById(category.gameId)
        updateGameScores(gameAGQBA)
    }

    suspend fun updateLightningCategoryInfo(lightningCategoryInfo: LightningCategoryInfo)  {
        lightningDao.updateLightningCategoryInfo(lightningCategoryInfo)
        val gameAGQBA = gameDao.getGameById(lightningCategoryInfo.gameId)
        updateGameScores(gameAGQBA)
    }

    suspend fun updateGameScores(gameAGQBA: GameAGQBA) {
        val newTeam1Score = gameAGQBA.team1TotalScore
        val newTeam2Score = gameAGQBA.team2TotalScore
        val newGame = gameAGQBA.game.copy(team1SavedScore = newTeam1Score, team2SavedScore = newTeam2Score)
        gameDao.updateGame(newGame)
    }

    private fun blankBonusCategories(gameId: Long) = (1..4).map { categoryNumber ->
            BonusCategoryInfo(
                null,
                gameId,
                categoryNumber,
                TeamAnswered.NONE,
                null
            )
        }

    private fun blankBonusQuestions(categoryId: Long) = (1..4).map { questionNumber ->
            BonusQuestion(
                categoryId,
                questionNumber,
                false,
                null
            )
        }

    private fun blankLightningCategory(gameId: Long, team: TeamAnswered) =
        LightningCategoryInfo(
            null,
            gameId,
            team,
            null
        )


    private fun blankLightningQuestions(lightningId: Long) = (1..10).map { questionNumber ->
            LightningQuestion(
                lightningId,
                questionNumber,
                LightningAnswer.STALLED,
                null
            )
        }

    private fun blankAGQBATossups(gameId: Long) =
        listOf(blankRound1Tossups(gameId), blankRound2Tossups(gameId), blankRound4Tossups(gameId))
            .flatten()

    private fun blankRound1Tossups(gameId: Long) = (1..20).map { questionNumber ->
            Tossup(
                null,
                gameId,
                questionNumber,
                1,
                TeamAnswered.NONE,
                null
            )
        }

    private fun blankRound2Tossups(gameId: Long) = (21..30).map { questionNumber ->
        Tossup(
            null,
            gameId,
            questionNumber,
            2,
            TeamAnswered.NONE,
            null
        )
    }

    private fun blankRound4Tossups(gameId: Long) = (31..50).map { questionNumber ->
        Tossup(
            null,
            gameId,
            questionNumber,
            4,
            TeamAnswered.NONE,
            null
        )
    }
}