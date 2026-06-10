package com.example.wwmweaponscompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WeaponViewModelFactory(private val screenTitle: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeaponViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeaponViewModel(screenTitle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}