package com.example.quizbowlscoresheet.database.models

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

enum class TeamAnswered {
    TEAM1,
    TEAM2,
    NONE
}

@ProvidedTypeConverter
class TeamAnsweredConverter {
    @TypeConverter
    fun toTeamAnswered(value: Int) = enumValues<TeamAnswered>()[value]

    @TypeConverter
    fun fromTeamAnswered(value: TeamAnswered) = value.ordinal
}