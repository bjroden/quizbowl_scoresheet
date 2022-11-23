package com.example.quizbowlscoresheet.database.databases.tossups

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quizbowlscoresheet.database.models.Tossup
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(Tossup::class), version = 1, exportSchema = false)
public abstract class TossupDatabase: RoomDatabase() {
    abstract fun tossupDao(): TossupDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time
        @Volatile
        private var INSTANCE: TossupDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): TossupDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?:  synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TossupDatabase::class.java,
                    "todoitem_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
