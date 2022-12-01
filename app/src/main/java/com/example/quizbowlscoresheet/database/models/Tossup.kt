package com.example.quizbowlscoresheet.database.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = arrayOf(ForeignKey(entity = Game::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("gameId"),
        onDelete = CASCADE)
    )
)
data class Tossup(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,

    val gameId: Long?,

    val questionNumber: Int,

    val roundNumber: Int,

    val team: TeamAnswered,

    val text: String?
)