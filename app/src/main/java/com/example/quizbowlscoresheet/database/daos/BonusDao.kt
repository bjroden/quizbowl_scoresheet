package com.example.quizbowlscoresheet.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.quizbowlscoresheet.database.models.BonusCategoryInfo
import com.example.quizbowlscoresheet.database.models.BonusQuestion

@Dao
interface BonusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBonusQuestion(bonusQuestion: BonusQuestion): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBonusQuestions(bonusQuestions: List<BonusQuestion>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBonusCategoryInfo(bonusCategory: BonusCategoryInfo): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBonusCategoryInfoList(list: List<BonusCategoryInfo>): List<Long>
}