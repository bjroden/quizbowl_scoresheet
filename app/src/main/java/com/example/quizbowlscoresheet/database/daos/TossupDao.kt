package com.example.quizbowlscoresheet.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.quizbowlscoresheet.database.models.Tossup
import kotlinx.coroutines.flow.Flow

@Dao
interface TossupDao {
    @Query("SELECT * FROM Tossup order by id ASC")
    fun getTossups(): Flow<List<Tossup>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTossup(tossup: Tossup): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTossupList(tossup: List<Tossup>): List<Long>

    @Update
    suspend fun updateTossup(tossup: Tossup)
}