package com.example.vehicle.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehicle.common.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.vehicle.model.VehicleData
import com.example.vehicle.repository.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DataViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<VehicleData>>(UIState.Loading)
    val uiState: StateFlow<UIState<VehicleData>> get() = _uiState

    init {
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = dataRepository.getResponse()
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _uiState.value = UIState.Success(data)
                    } else {
                        _uiState.value = UIState.Error("No data available")
                    }
                } else {
                    _uiState.value = UIState.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error("Exception: ${e.message}")
            }
        }
    }
}
