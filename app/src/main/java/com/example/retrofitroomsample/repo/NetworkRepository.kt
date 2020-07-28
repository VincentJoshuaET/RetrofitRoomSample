package com.example.retrofitroomsample.repo

import com.example.retrofitroomsample.service.ApiService
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val service: ApiService) {
    suspend fun getUsers(start: Int, limit: Int) = service.getUsers(start, limit)
}