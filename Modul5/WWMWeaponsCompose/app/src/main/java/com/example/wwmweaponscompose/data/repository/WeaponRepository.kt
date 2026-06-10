package com.example.wwmweaponscompose.data.repository

import com.example.wwmweaponscompose.data.local.WeaponDao
import com.example.wwmweaponscompose.data.remote.WeaponApiService
import com.example.wwmweaponscompose.model.ApiResult
import com.example.wwmweaponscompose.model.Weapon
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException

class WeaponRepository(
    private val apiService: WeaponApiService,
    private val weaponDao: WeaponDao
) {
    fun getAllWeapons(): Flow<List<Weapon>> = weaponDao.getAllWeaponsFlow()

    suspend fun refreshWeapons(): ApiResult<Unit> {
        return try {
            val remoteWeapons = apiService.getWeapons()
            weaponDao.insertWeapons(remoteWeapons)
            ApiResult.Success(Unit)
        } catch (e: IOException) {
            ApiResult.Error("Tidak ada koneksi internet. Menampilkan data offline.")
        } catch (e: HttpException) {
            ApiResult.Error("Terjadi kesalahan pada server API.")
        } catch (e: Exception) {
            ApiResult.Error(e.localizedMessage ?: "Terjadi kesalahan tidak terduga.")
        }
    }

    suspend fun getWeaponById(id: Int): Weapon? {
        return weaponDao.getWeaponById(id)
    }
}