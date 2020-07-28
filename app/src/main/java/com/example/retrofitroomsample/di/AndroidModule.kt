package com.example.retrofitroomsample.di

import android.content.Context
import android.content.SharedPreferences
import android.view.inputmethod.InputMethodManager
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AndroidModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context) = context

    @Provides
    @Singleton
    fun provideInputMethodManager(@ApplicationContext context: Context): InputMethodManager =
        context.getSystemService(InputMethodManager::class.java)

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

}