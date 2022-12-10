package com.example.quizbowlscoresheet.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.quizbowlscoresheet.database.models.Game
import com.example.quizbowlscoresheet.database.models.GameAGQBA
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Transaction
    @Query("SELECT * from Game")
    fun getGames(): Flow<List<GameAGQBA>>

    @Query("SELECT * FROM Game where :id = id")
    fun getGameById(id: Long): Flow<GameAGQBA>

    @Query("SELECT * FROM Game where :id = id")
    suspend fun getGameNoFlowById(id: Long): GameAGQBA

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: Game): Long

    @Update
    suspend fun updateGame(game: Game)
}