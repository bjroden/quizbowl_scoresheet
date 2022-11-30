package com.example.quizbowlscoresheet.database.models

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

enum class LightningAnswer {
    CORRECT,
    INCORRECT,
    BOUNCED_BACK,
    STALLED
}

@ProvidedTypeConverter
class LightningAnswerConverter {
    @TypeConverter
    fun toLightningAnswer(value: Int) = enumValues<LightningAnswer>()[value]

    @TypeConverter
    fun toLightningAnswer(value: LightningAnswer) = value.ordinal
}
