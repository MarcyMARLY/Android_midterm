package com.example.msise.androidmid.DAO

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.msise.androidmid.model.UserData

@Dao
interface UserDataDao {

    @Query("SELECT * from userdata")
    fun getAll(): List<UserData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userData: UserData)

    @Query("DELETE from userData")
    fun deleteAll()
}