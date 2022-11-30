package com.example.quizbowlscoresheet.database.repositories.agqbagame

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quizbowlscoresheet.database.daos.GameDao
import com.example.quizbowlscoresheet.database.daos.RoundDao
import com.example.quizbowlscoresheet.database.daos.TeamDao
import com.example.quizbowlscoresheet.database.daos.TossupDao
import com.example.quizbowlscoresheet.database.models.Game
import com.example.quizbowlscoresheet.database.models.RoundsAGQBAInfo
import com.example.quizbowlscoresheet.database.models.Team
import com.example.quizbowlscoresheet.database.models.Tossup
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = arrayOf(Game::class, Tossup::class, Team::class, RoundsAGQBAInfo::class),
    version = 1, exportSchema = false
)
public abstract class GameAGQBADatabase: RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun tossupDao(): TossupDao
    abstract fun teamDao(): TeamDao
    abstract fun roundDao(): RoundDao

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
