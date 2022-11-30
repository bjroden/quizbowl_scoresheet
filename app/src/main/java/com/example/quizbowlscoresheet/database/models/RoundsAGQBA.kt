package com.example.quizbowlscoresheet.database.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

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
data class RoundsAGQBAInfo (
    @PrimaryKey(autoGenerate = false)
    val gameId: Long
)

data class RoundsAGQBA(
    @Embedded
    val roundsInfo: RoundsAGQBAInfo
)