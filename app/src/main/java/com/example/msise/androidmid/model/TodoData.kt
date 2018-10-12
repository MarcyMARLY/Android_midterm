package com.example.msise.androidmid.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "todoData",
        foreignKeys = arrayOf(
                ForeignKey(entity = UserData::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("emailId"),
                        onDelete = CASCADE)))
data class TodoData(
        @PrimaryKey(autoGenerate = true) var id: Long?,
        @ColumnInfo(name = "title") var title: String,
        @ColumnInfo(name = "flag") var flag: Int,
        @ColumnInfo(name = "emailId") var emailId: String
) {
    constructor() : this(null, "", 1,"")
}