package com.example.quizbowlscoresheet.database.databases.tossups

import androidx.annotation.WorkerThread
import com.example.quizbowlscoresheet.database.models.Tossup
import kotlinx.coroutines.flow.Flow

class TossupRepository(private val tossupDao: TossupDao) {
    val allTossups: Flow<List<Tossup>> = tossupDao.getTossups()

    @WorkerThread
    suspend fun insert(tossup: Tossup): Long = tossupDao.insertTossup(tossup)

    @WorkerThread
    suspend fun insertList(tossups: List<Tossup>): List<Long> = tossupDao.insertTossupList(tossups)
}