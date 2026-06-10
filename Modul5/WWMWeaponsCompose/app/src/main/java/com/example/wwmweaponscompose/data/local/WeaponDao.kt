package com.example.wwmweaponscompose.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wwmweaponscompose.model.Weapon
import kotlinx.coroutines.flow.Flow

@Dao
interface WeaponDao {
    @Query("SELECT * FROM weapons")
    fun getAllWeaponsFlow(): Flow<List<Weapon>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertWeapons(weapons: List<Weapon>)

    @Query("SELECT * FROM weapons WHERE id = :weaponId")
    suspend fun getWeaponById(weaponId: Int): Weapon?
}