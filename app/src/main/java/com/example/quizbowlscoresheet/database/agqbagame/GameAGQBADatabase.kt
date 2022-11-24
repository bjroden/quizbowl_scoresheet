package com.example.quizbowlscoresheet.database.agqbagame

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quizbowlscoresheet.database.models.Game
import com.example.quizbowlscoresheet.database.models.GameAGQBA
import com.example.quizbowlscoresheet.database.models.Tossup
import kotlinx.coroutines.CoroutineScope
@Database(entities = arrayOf(Game::class, Tossup::class), version = 1, exportSchema = false)
public abstract class GameAGQBADatabase: RoomDatabase() {
    abstract fun gameDao(): GameAGQBADao

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
