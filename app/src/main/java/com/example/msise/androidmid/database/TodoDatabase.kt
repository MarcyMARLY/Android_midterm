package com.example.msise.androidmid.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.msise.androidmid.DAO.TodoDataDao
import com.example.msise.androidmid.model.TodoData
import com.example.msise.androidmid.model.UserData

@Database(entities = arrayOf(TodoData::class, UserData::class), version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDataDao(): TodoDataDao

    companion object {
        private var INSTANCE: TodoDatabase? = null

        fun getInstance(context: Context): TodoDatabase? {
            if (INSTANCE == null) {
                synchronized(TodoDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            TodoDatabase::class.java, "todos.db")
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