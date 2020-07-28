package com.example.retrofitroomsample.repo

import com.example.retrofitroomsample.room.UserDao
import com.example.retrofitroomsample.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val dao: UserDao) {
    suspend fun getUser(email: String) = dao.getUser(email)
    suspend fun login(email: String, password: String) = dao.login(email, password)
    suspend fun check(email: String) = dao.checkEmail(email)
    suspend fun register(user: User) = dao.register(user)
}