package com.example.quizbowlscoresheet.database.models

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

enum class LightningAnswer {
    CORRECT,        // Team got it
    INCORRECT,      // Neither team got it
    BOUNCED_BACK,   // Team missed, other team got it
    STALLED         // Stall
}

@ProvidedTypeConverter
class LightningAnswerConverter {
    @TypeConverter
    fun toLightningAnswer(value: Int) = enumValues<LightningAnswer>()[value]

    @TypeConverter
    fun toLightningAnswer(value: LightningAnswer) = value.ordinal
}
