package com.example.quizbowlscoresheet.database.models

import androidx.room.Entity

@Entity(primaryKeys = arrayOf("categoryId", "questionNumber"))
data class BonusQuestion(
    val categoryId: Long,

    val questionNumber: Int,

    val answered: Boolean,

    val text: String?
)