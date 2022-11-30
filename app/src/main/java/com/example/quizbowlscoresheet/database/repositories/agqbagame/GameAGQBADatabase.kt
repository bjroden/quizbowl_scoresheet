package com.example.quizbowlscoresheet.database.repositories.agqbagame

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quizbowlscoresheet.database.daos.*
import com.example.quizbowlscoresheet.database.models.*
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = arrayOf(
        Game::class,
        Tossup::class,
        Team::class,
        BonusCategoryInfo::class,
        BonusQuestion::class,
        LightningCategoryInfo::class,
        LightningQuestion::class
    ),
    version = 1,
    exportSchema = false)
public abstract class GameAGQBADatabase: RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun tossupDao(): TossupDao
    abstract fun teamDao(): TeamDao
    abstract fun bonusDao(): BonusDao
    abstract fun lightningDao(): LightningDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time
        @Volatile
        private var INSTANCE: GameAGQBADatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): GameAGQBADatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?:  synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameAGQBADatabase::class.java,
                    "todoitem_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
