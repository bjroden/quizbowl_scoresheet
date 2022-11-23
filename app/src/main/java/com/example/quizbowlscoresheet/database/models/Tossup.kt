package com.example.quizbowlscoresheet.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tossups_table")
data class Tossup(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name = "team_answered")
    val team: TeamAnswered,

    @ColumnInfo(name = "question_text")
    val text: String?
)