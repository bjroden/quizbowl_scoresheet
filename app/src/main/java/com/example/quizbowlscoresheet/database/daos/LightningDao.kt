package com.example.quizbowlscoresheet.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.quizbowlscoresheet.database.models.LightningCategoryInfo
import com.example.quizbowlscoresheet.database.models.LightningQuestion

@Dao
interface LightningDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLightningQuestion(question: LightningQuestion): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLightningQuestions(questions: List<LightningQuestion>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLightningCategoryInfo(info: LightningCategoryInfo): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLightningCategoryInfoList(list: List<LightningCategoryInfo>): List<Long>
}