package com.example.retrofitroomsample.service

import com.example.retrofitroomsample.model.Item
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/users")
    suspend fun getUsers(@Query("_start") start: Int, @Query("_limit") limit: Int): List<Item>
}