package com.example.demoLogin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.demoLogin.data.dao.AssociatesPersonDao
import com.example.demoLogin.data.model.PersonNew

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(PersonNew::class), version = 2, exportSchema = false)
public abstract class AppDBNew : RoomDatabase() {

    abstract fun personDaoNew(): AssociatesPersonDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDBNew? = null

        fun getDatabase(context: Context): AppDBNew {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDBNew::class.java,
                    "personlist"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
