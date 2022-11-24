package com.example.quizbowlscoresheet.database.models

import androidx.room.Embedded
import androidx.room.Relation

data class GameAGQBA(
    @Embedded
    val game: Game,

    @Relation(
        parentColumn = "id",
        entityColumn = "game_id"
    )
    val tossups: List<Tossup>
)