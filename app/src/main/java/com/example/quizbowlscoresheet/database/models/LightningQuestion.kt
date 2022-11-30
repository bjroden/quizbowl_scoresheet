package com.example.quizbowlscoresheet.database.models

import androidx.room.Entity

@Entity(primaryKeys = arrayOf("categoryId", "questionNumber"))
data class LightningQuestion(
    val categoryId: Long,

    val questionNumber: Int,

    val answer: LightningAnswer,

    val text: String?
)