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

    val allGameAGQBA: Flow<List<GameAGQBA>> = gameDao.getGames()

    @WorkerThread
    suspend fun insertGameAGQBA(gameAGQBA: GameAGQBA) {
        gameDao.insertGame(gameAGQBA.game)
        for (i in gameAGQBA.tossups) {
            tossupDao.insertTossup(i)
        }
    }

    @WorkerThread
    suspend fun newGameAGQBA() = database.withTransaction {
        // TODO: have real team input
        val team1 = teamDao.insertTeam(Team(null, "team 1!"))
        val team2 = teamDao.insertTeam(Team(null, "team 2!"))
        val gameId = gameDao.insertGame(Game(null, team1, team2))
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
    }

    private fun blankBonusCategories(gameId: Long) = List(4) { categoryNumber ->
            BonusCategoryInfo(
                null,
                gameId,
                categoryNumber,
                null
            )
        }

    private fun blankBonusQuestions(categoryId: Long) = List(4) { questionNumber ->
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


    private fun blankLightningQuestions(lightningId: Long) = List(10) { questionNumber ->
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