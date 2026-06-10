package com.example.wwmweaponscompose.data.remote

import com.example.wwmweaponscompose.model.Weapon
import retrofit2.http.GET

interface WeaponApiService {
    @GET("v1/b13c2025-ea54-4b01-8807-6b7d2cda3596")
    suspend fun getWeapons(): List<Weapon>
}