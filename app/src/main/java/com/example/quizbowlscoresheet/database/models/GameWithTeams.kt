package com.example.quizbowlscoresheet.database.models

import androidx.room.Embedded
import androidx.room.Relation

data class GameWithTeams(
    @Embedded
    val game: Game,

    @Relation(
        parentColumn = "team1Id",
        entityColumn = "id"
    )
    val team1: Team?,

    @Relation(
        parentColumn = "team2Id",
        entityColumn = "id"
    )
    val team2: Team?
)