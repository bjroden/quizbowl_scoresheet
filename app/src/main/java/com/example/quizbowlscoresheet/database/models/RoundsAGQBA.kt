package com.example.quizbowlscoresheet.database.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RoundsAGQBAInfo (
    @PrimaryKey(autoGenerate = false)
    val gameId: Long
)

data class RoundsAGQBA(
    @Embedded
    val roundsInfo: RoundsAGQBAInfo
)