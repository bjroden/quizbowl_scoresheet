package com.example.quizbowlscoresheet.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "tossups_table",
    foreignKeys = arrayOf(ForeignKey(entity = Game::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("game_id"),
        onDelete = CASCADE)
    )
)
data class Tossup(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name = "game_id")
    val gameId: Int?,

    @ColumnInfo(name = "question_number")
    val questionNumber: Int,

    @ColumnInfo(name = "round_number")
    val roundNumber: Int,

    @ColumnInfo(name = "team_answered")
    val team: TeamAnswered,

    @ColumnInfo(name = "question_text")
    val text: String?
)