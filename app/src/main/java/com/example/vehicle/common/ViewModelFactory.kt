package com.example.vehicle.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vehicle.repository.DataRepository
import com.example.vehicle.viewmodel.MainActivityViewModel


class DataViewModelFactory(private val dataRepository: DataRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(dataRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}