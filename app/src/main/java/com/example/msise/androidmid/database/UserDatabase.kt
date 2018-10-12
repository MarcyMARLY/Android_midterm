package com.example.msise.androidmid.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.msise.androidmid.DAO.UserDataDao
import com.example.msise.androidmid.model.TodoData
import com.example.msise.androidmid.model.UserData

@Database(entities = arrayOf(UserData::class), version = 1)
abstract class UserDatabase : RoomDatabase(){
    abstract fun userDataDao(): UserDataDao

    companion object {
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase? {
            if (INSTANCE == null) {
                synchronized(UserDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            UserDatabase::class.java, "users.db")
                            .build()
                }
            }

            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}