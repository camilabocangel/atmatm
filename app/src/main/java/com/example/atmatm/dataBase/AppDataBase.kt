package com.example.atmatm.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)

abstract class AppDataBase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object{
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this){
                val room = Room
                    .databaseBuilder(
                        context,
                        AppDataBase::class.java,
                        "ATM_database"
                    ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = room
                room
            }
        }
    }
}