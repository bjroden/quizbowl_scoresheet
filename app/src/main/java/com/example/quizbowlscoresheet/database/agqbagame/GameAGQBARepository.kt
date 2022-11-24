package com.example.quizbowlscoresheet.database.agqbagame

import androidx.annotation.WorkerThread
import com.example.quizbowlscoresheet.database.databases.tossups.TossupDao
import com.example.quizbowlscoresheet.database.models.Game
import com.example.quizbowlscoresheet.database.models.GameAGQBA
import com.example.quizbowlscoresheet.database.models.Tossup
import kotlinx.coroutines.flow.Flow

class GameAGQBARepository(
    private val gameAGQBADao: GameAGQBADao,
    private val tossupDao: TossupDao
) {
    val allGameAGQBA: Flow<List<GameAGQBA>> = gameAGQBADao.getGames()
    val allTossups: Flow<List<Tossup>> = tossupDao.getTossups()

    @WorkerThread
    suspend fun insertGame(game: Game) {
        gameAGQBADao.insertGame(game)
    }

    @WorkerThread
    suspend fun insertGameAGQBA(gameAGQBA: GameAGQBA) {
        gameAGQBADao.insertGame(gameAGQBA.game)
        for (i in gameAGQBA.tossups) {
            tossupDao.insertTossup(i)
        }
    }
}