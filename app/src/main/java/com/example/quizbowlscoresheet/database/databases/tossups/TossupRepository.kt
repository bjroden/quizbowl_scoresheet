package com.example.quizbowlscoresheet.database.databases.tossups

import androidx.annotation.WorkerThread
import com.example.quizbowlscoresheet.database.models.Tossup
import kotlinx.coroutines.flow.Flow

class TossupRepository(private val tossupDao: TossupDao) {
    val allTossups: Flow<List<Tossup>> = tossupDao.getTossups()

    @WorkerThread
    suspend fun insert(tossup: Tossup) {
        tossupDao.insertTossup(tossup)
    }
}