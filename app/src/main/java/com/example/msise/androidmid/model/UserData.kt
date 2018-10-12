package com.example.msise.androidmid.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "userData")
data class UserData(
        @PrimaryKey(autoGenerate = true) var id: Long?,
        @ColumnInfo(name = "email") var email: String,
        @ColumnInfo(name = "password") var password: String
) {
    constructor() : this(null, "", "")
}