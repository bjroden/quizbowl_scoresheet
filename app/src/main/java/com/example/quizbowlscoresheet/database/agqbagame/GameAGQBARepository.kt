package com.example.quizbowlscoresheet.database.agqbagame

import androidx.annotation.WorkerThread
import com.example.quizbowlscoresheet.database.models.Game
import com.example.quizbowlscoresheet.database.models.GameAGQBA
import kotlinx.coroutines.flow.Flow

class GameAGQBARepository(private val gameAGQBADao: GameAGQBADao) {
    val allTossups: Flow<List<Game>> = gameAGQBADao.getGames()

    @WorkerThread
    suspend fun insert(game: Game) {
        gameAGQBADao.insertGame(game)
    }
}