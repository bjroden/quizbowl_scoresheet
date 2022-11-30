package com.example.quizbowlscoresheet.database.models

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Game::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("gameId"),
            onDelete = CASCADE
        )
    )
)
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