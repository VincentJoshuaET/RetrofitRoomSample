package com.example.retrofitroomsample.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.retrofitroomsample.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUser(email: String): User

    @Query("SELECT EXISTS(SELECT * FROM users WHERE email = :email AND password = :password)")
    suspend fun login(email: String, password: String): Boolean

    @Query("SELECT EXISTS(SELECT * FROM users WHERE email = :email)")
    suspend fun checkEmail(email: String): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun register(user: User)

}