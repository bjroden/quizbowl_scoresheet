package com.example.quizbowlscoresheet

import android.app.Application
import com.example.quizbowlscoresheet.database.databases.tossups.TossupDatabase
import com.example.quizbowlscoresheet.database.databases.tossups.TossupRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class QuizbowlApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { TossupDatabase.getDatabase(this, applicationScope)}
    val repository by lazy { TossupRepository(database.tossupDao()) }
}
