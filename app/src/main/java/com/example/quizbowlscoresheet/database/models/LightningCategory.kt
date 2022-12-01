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