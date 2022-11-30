package com.example.quizbowlscoresheet.database.models

import androidx.room.Embedded
import androidx.room.Relation

data class GameAGQBA(
    @Embedded
    val game: Game,

    @Relation(
        parentColumn = "id",
        entityColumn = "gameId"
    )
    val tossups: List<Tossup>,

    @Relation(
        entity = BonusCategoryInfo::class,
        parentColumn = "id",
        entityColumn = "gameId"
    )
    val bonusCategories: List<BonusCategory>,

    @Relation(
        parentColumn = "team1Id",
        entityColumn = "id"
    )
    val team1: Team,

    @Relation(
        parentColumn = "team2Id",
        entityColumn = "id"
    )
    val team2: Team
)