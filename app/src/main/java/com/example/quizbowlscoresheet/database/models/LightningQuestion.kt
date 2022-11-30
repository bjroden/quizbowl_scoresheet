package com.example.quizbowlscoresheet.database.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(
    primaryKeys = arrayOf("categoryId", "questionNumber"),
    foreignKeys = arrayOf(
        ForeignKey(
            entity = LightningCategoryInfo::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("categoryId"),
            onDelete = CASCADE
        )
    )
)
data class LightningQuestion(
    val categoryId: Long,

    val questionNumber: Int,

    val answer: LightningAnswer,

    val text: String?
)