package com.example.quizbowlscoresheet.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Game (
    @PrimaryKey(autoGenerate = true)
    val id: Long?,

    val team1Id: Long?,

    val team2Id: Long?
)