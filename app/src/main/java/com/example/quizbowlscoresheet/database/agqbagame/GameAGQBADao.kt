package com.example.quizbowlscoresheet.database.agqbagame

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.quizbowlscoresheet.database.models.Game
import com.example.quizbowlscoresheet.database.models.GameAGQBA
import kotlinx.coroutines.flow.Flow

@Dao
interface GameAGQBADao {
    @Transaction
    @Query("SELECT * from game_table")
    fun getGames(): Flow<List<GameAGQBA>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: Game): Long
}