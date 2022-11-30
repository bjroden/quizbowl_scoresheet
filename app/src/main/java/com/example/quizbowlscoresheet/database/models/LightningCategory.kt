package com.example.quizbowlscoresheet.database.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class LightningCategoryInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,

    val gameId: Long,

    val team: TeamAnswered,

    val name: String?
)

data class LightningCategory(
    @Embedded
    val categoryInfo: LightningCategoryInfo,

    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val questions: List<LightningQuestion>
)