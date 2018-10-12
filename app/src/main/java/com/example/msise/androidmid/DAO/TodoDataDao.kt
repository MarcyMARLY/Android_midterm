package com.example.msise.androidmid.DAO

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.msise.androidmid.model.TodoData

@Dao
interface TodoDataDao {
    @Query("SELECT * from todoData")
    fun getAll(): List<TodoData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todoData: TodoData)

    @Query("DELETE from todoData")
    fun deleteAll()
}