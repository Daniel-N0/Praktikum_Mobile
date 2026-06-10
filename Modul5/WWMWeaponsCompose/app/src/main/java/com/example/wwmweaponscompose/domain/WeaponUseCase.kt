package com.example.wwmweaponscompose.domain

import com.example.wwmweaponscompose.data.repository.WeaponRepository
import com.example.wwmweaponscompose.model.ApiResult
import com.example.wwmweaponscompose.model.Weapon
import kotlinx.coroutines.flow.Flow

class WeaponUseCase(private val repository: WeaponRepository) {

    fun getWeaponsFlow(): Flow<List<Weapon>> {
        return repository.getAllWeapons()
    }

    suspend fun syncWeapons(): ApiResult<Unit> {
        return repository.refreshWeapons()
    }

    suspend fun getWeaponDetail(id: Int): Weapon? {
        return repository.getWeaponById(id)
    }
}