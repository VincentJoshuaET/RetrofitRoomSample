package com.example.retrofitroomsample.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.retrofitroomsample.model.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}