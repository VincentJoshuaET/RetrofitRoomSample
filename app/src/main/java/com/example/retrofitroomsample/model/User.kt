package com.example.retrofitroomsample.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val email: String,
    val password: String,
    val name: String,
    val mobile: String,
    val country: String,
    val gender: String,
    val dateOfBirth: Long
)