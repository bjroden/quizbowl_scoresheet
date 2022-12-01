package com.example.quizbowlscoresheet.database.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(
    primaryKeys = arrayOf("categoryId", "questionNumber"),
    foreignKeys = arrayOf(
        ForeignKey(
            entity = BonusCategoryInfo::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("categoryId"),
            onDelete = CASCADE
        )
    )
)
data class BonusQuestion(
    val categoryId: Long,

    val questionNumber: Int,

    val answered: Boolean,

    val text: String?
)