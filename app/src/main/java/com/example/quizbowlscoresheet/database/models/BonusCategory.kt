package com.example.quizbowlscoresheet.database.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class BonusCategoryInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,

    val gameId: Long,

    val categoryNumber: Int,

    val name: String?,
)

data class BonusCategory(
    @Embedded
    val categoryInfo: BonusCategoryInfo,

    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val bonusQuestions: List<BonusQuestion>
)