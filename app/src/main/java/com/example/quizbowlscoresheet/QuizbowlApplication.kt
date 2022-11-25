package com.example.quizbowlscoresheet

import android.app.Application
import com.example.quizbowlscoresheet.database.repositories.agqbagame.GameAGQBADatabase
import com.example.quizbowlscoresheet.database.repositories.agqbagame.GameAGQBARepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class QuizbowlApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { GameAGQBADatabase.getDatabase(this, applicationScope)}
    val repository by lazy { GameAGQBARepository(database.gameDao(), database.tossupDao(), database.teamDao()) }
}
