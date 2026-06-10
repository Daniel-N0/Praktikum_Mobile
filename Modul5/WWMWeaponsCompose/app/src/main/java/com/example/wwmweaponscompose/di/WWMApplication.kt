package com.example.wwmweaponscompose.di

import android.app.Application
import com.example.wwmweaponscompose.data.local.UserPreferences
import com.example.wwmweaponscompose.data.local.WeaponDatabase
import com.example.wwmweaponscompose.data.remote.WeaponApiService
import com.example.wwmweaponscompose.data.repository.WeaponRepository
import com.example.wwmweaponscompose.domain.WeaponUseCase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import timber.log.Timber

class WWMApplication : Application() {
    lateinit var useCase: WeaponUseCase
    lateinit var userPreferences: UserPreferences

    override fun onCreate() {
        super.onCreate()
        Timber.Forest.plant(Timber.DebugTree())
        userPreferences = UserPreferences(this)

        val database = WeaponDatabase.Companion.getDatabase(this)

        val json = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://mocki.io/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        val apiService = retrofit.create(WeaponApiService::class.java)

        val repository = WeaponRepository(apiService, database.weaponDao())
        useCase = WeaponUseCase(repository)
    }
}