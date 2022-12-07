package com.example.quizbowlscoresheet.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.quizbowlscoresheet.database.models.BonusCategory
import com.example.quizbowlscoresheet.database.models.BonusCategoryInfo
import com.example.quizbowlscoresheet.database.models.BonusQuestion
import kotlinx.coroutines.flow.Flow

@Dao
interface BonusDao {
    @Query("SELECT * FROM bonuscategoryinfo WHERE gameId = :gameId AND categoryNumber = :categoryNumber")
    fun getBonusCategoryByNumber(gameId: Long, categoryNumber: Int): Flow<BonusCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBonusQuestion(bonusQuestion: BonusQuestion): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBonusQuestions(bonusQuestions: List<BonusQuestion>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBonusCategoryInfo(bonusCategory: BonusCategoryInfo): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBonusCategoryInfoList(list: List<BonusCategoryInfo>): List<Long>

    @Update
    suspend fun updateBonusQuestion(bonusQuestion: BonusQuestion)

    @Update
    suspend fun updateBonusCategoryInfo(bonusCategory: BonusCategoryInfo)
}