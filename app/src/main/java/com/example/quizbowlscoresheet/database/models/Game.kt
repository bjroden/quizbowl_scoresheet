package com.example.quizbowlscoresheet.database.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.SET_NULL
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Team::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("team1Id"),
            onDelete = SET_NULL
        ),
        ForeignKey(
            entity = Team::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("team2Id"),
            onDelete = SET_NULL
        )
    )
)
data class Game (
    @PrimaryKey(autoGenerate = true)
    val id: Long?,

    val team1Id: Long?,

    val team2Id: Long?,

    val team1SavedScore: Int,

    val team2SavedScore: Int
)