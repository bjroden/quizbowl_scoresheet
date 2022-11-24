package com.example.quizbowlscoresheet.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_table")
data class Game (
    @PrimaryKey(autoGenerate = true)
    val id: Long?
)