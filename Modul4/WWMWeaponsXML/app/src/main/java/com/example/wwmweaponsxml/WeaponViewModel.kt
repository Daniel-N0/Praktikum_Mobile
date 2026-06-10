package com.example.wwmweaponsxml

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class WeaponViewModel(val screenTitle: String) : ViewModel() {

    private val _weaponList = MutableStateFlow<List<Weapon>>(emptyList())
    val weaponList: StateFlow<List<Weapon>> = _weaponList.asStateFlow()

    init {
        loadWeapons()
    }

    private fun loadWeapons() {
        val data = WeaponDataSource.dummyWeapons
        _weaponList.value = data

        Timber.d("Log Data Masuk: Data senjata berhasil dimuat. Total data: ${data.size}")
    }

    fun onDetailClicked(weapon: Weapon) {
        Timber.d("Log Tombol: Tombol Detail ditekan")

        Timber.d("Log Item Dipilih: Berpindah ke Detail untuk senjata - ${weapon.name}")
    }

    fun onExplicitIntentClicked(weapon: Weapon) {
        Timber.d("Log Tombol: Tombol Explicit Intent (Web Info) ditekan")
    }
}