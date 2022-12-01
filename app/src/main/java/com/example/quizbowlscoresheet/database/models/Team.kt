package com.example.quizbowlscoresheet.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Team(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,

    val name: String
)
