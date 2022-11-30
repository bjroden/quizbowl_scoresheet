package com.example.quizbowlscoresheet.database.daos

import androidx.room.*
import com.example.quizbowlscoresheet.database.models.RoundsAGQBAInfo

@Dao
interface RoundDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoundsAGQBQAInfo(roundsAGQBAInfo: RoundsAGQBAInfo): Long
}