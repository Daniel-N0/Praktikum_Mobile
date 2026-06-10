package com.example.wwmweaponscompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wwmweaponscompose.domain.WeaponUseCase

class WeaponViewModelFactory(
    private val screenTitle: String,
    private val useCase: WeaponUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeaponViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeaponViewModel(screenTitle, useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}