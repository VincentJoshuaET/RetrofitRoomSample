package com.example.retrofitroomsample.repo

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val preferences: SharedPreferences) {
    fun login(email: String) {
        preferences.edit().putString("email", email).apply()
        preferences.edit().putBoolean("authenticated", true).apply()
    }
    fun logout() {
        preferences.edit().putBoolean("authenticated", false).apply()
        preferences.edit().remove("email").apply()
    }
    fun email() = preferences.getString("email", null)
    fun isLoggedIn() = preferences.getBoolean("authenticated", false)
}