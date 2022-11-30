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

    val allGameAGQBA: Flow<List<GameAGQBA>> = gameDao.getGames()
    val allTossups: Flow<List<Tossup>> = tossupDao.getTossups()
    val allTeams: Flow<List<Team>> = teamDao.getTeams()

    @WorkerThread
    suspend fun insertGame(game: Game): Long = gameDao.insertGame(game)

    @WorkerThread
    suspend fun insertGameAGQBA(gameAGQBA: GameAGQBA) {
        gameDao.insertGame(gameAGQBA.game)
        for (i in gameAGQBA.tossups) {
            tossupDao.insertTossup(i)
        }
    }

    @WorkerThread
    suspend fun insertTossup(tossup: Tossup): Long = tossupDao.insertTossup(tossup)

    @WorkerThread
    suspend fun insertTossupList(tossups: List<Tossup>): List<Long> = tossupDao.insertTossupList(tossups)

    @WorkerThread
    suspend fun insertTeam(team: Team): Long = teamDao.insertTeam(team)

    @WorkerThread
    suspend fun insertTeamList(teams: List<Team>): List<Long> = teamDao.insertTeamList(teams)

    @WorkerThread
    suspend fun insertBonusQuestion(bonusQuestion: BonusQuestion): Long = bonusDao.insertBonusQuestion(bonusQuestion)

    @WorkerThread
    suspend fun insertBonusQuestions(bonusQuestions: List<BonusQuestion>): List<Long> = bonusDao.insertBonusQuestions(bonusQuestions)

    @WorkerThread
    suspend fun insertBonusCategoryInfo(bonusCategoryInfo: BonusCategoryInfo): Long = bonusDao.insertBonusCategoryInfo(bonusCategoryInfo)

    @WorkerThread
    suspend fun insertBonusCategoryInfoList(list: List<BonusCategoryInfo>): List<Long> = bonusDao.insertBonusCategoryInfoList(list)

    @WorkerThread
    suspend fun newGameAGQBA() = database.withTransaction {
        // TODO: have real team input
        val team1 = insertTeam(Team(null, "team 1!"))
        val team2 = insertTeam(Team(null, "team 2!"))
        val gameId = insertGame(Game(null, team1, team2))
        val round1Tossups = List(20) { questionNumber ->
            Tossup(
                null,
                gameId,
                questionNumber,
                1,
                TeamAnswered.NONE,
                null
            )
        }
        insertTossupList(round1Tossups)
        val bonusCategories = List(4) { categoryNumber ->
            BonusCategoryInfo(
                null,
                gameId,
                categoryNumber,
                null
            )
        }
        val categoryIds = insertBonusCategoryInfoList(bonusCategories)
        val bonusQuestions = categoryIds.flatMap { id ->
            List(4) { questionNumber ->
                BonusQuestion(
                    id,
                    questionNumber,
                    false,
                    null
                )
            }
        }
        insertBonusQuestions(bonusQuestions)
    }
}