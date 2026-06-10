package com.example.wwmweaponscompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wwmweaponscompose.domain.WeaponUseCase
import com.example.wwmweaponscompose.model.ApiResult
import com.example.wwmweaponscompose.model.Weapon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class WeaponViewModel(
    val screenTitle: String,
    private val useCase: WeaponUseCase
) : ViewModel() {

    private val _weaponList = MutableStateFlow<List<Weapon>>(emptyList())
    val weaponList: StateFlow<List<Weapon>> = _weaponList.asStateFlow()

    private val _apiState = MutableStateFlow<ApiResult<Unit>>(ApiResult.Loading)
    val apiState: StateFlow<ApiResult<Unit>> = _apiState.asStateFlow()

    init {
        observeLocalWeapons()
        refreshDataFromApi()
    }

    private fun observeLocalWeapons() {
        viewModelScope.launch {
            useCase.getWeaponsFlow().collect { weapons ->
                _weaponList.value = weapons
            }
        }
    }

    fun refreshDataFromApi() {
        viewModelScope.launch {
            _apiState.value = ApiResult.Loading
            val result = useCase.syncWeapons()
            _apiState.value = result

            if (result is ApiResult.Error) {
                Timber.Forest.e("Gagal sync API: ${result.message}")
            } else {
                Timber.Forest.d("Berhasil narik data API dan menyimpannya ke Room Database")
            }
        }
    }

    fun onDetailClicked(weapon: Weapon) {
        Timber.Forest.d("Log Tombol: Tombol Detail ditekan")
        Timber.Forest.d("Log Item Dipilih: Berpindah ke Detail untuk senjata - ${weapon.name}")
    }

    fun onExplicitIntentClicked(weapon: Weapon) {
        Timber.Forest.d("Log Tombol: Tombol Explicit Intent (Web Info) ditekan")
    }
}