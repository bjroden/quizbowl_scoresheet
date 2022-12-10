package com.example.quizbowlscoresheet.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.quizbowlscoresheet.database.models.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LightningDao {
    // Convenience method to get TEAM1 or TEAM2. Don't use with NONE.
    @Query("SELECT * FROM LightningCategoryInfo WHERE gameId = :gameId AND team = :team")
    fun getLightningCategoryByTeam(gameId: Long, team: TeamAnswered): Flow<LightningCategory>

    @Query("SELECT * FROM LightningCategoryInfo WHERE id = :id")
    suspend fun getLightningCategoryInfoById(id: Long): LightningCategoryInfo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLightningQuestion(question: LightningQuestion): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLightningQuestionList(questions: List<LightningQuestion>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLightningCategoryInfo(info: LightningCategoryInfo): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLightningCategoryInfoList(list: List<LightningCategoryInfo>): List<Long>

    @Update
    suspend fun updateLightningQuestion(lightningQuestion: LightningQuestion)

    @Update
    suspend fun updateLightningCategoryInfo(lightningCategoryInfo: LightningCategoryInfo)
}