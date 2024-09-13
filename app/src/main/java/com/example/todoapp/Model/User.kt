package com.example.todoapp.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "User", indices = [Index(value = ["email", "username"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val email: String,
    val username: String,
    val password: String
)