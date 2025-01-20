package com.example.vehicle.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehicle.common.UIState
import com.example.vehicle.model.evGraph.EvSalesGraphData
import com.example.vehicle.model.Timeline
import com.example.vehicle.model.avergaePowerSupplyTraiff.AverageSupplyTariff
import com.example.vehicle.model.lastMonthSales.LastMonthSalesData
import com.example.vehicle.model.salesManufacturers.SalesManufacturers
import com.example.vehicle.model.totalChargeStations.TotalChargeStations
import com.example.vehicle.model.totalLastMonthSales.TotalLastMonthSalesData
import com.example.vehicle.model.totalSales.TotalSalesData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.vehicle.repository.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainActivityViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private val _evGraphData = MutableStateFlow<UIState<EvSalesGraphData>>(UIState.Loading)
    val evGraphData: StateFlow<UIState<EvSalesGraphData>> get() = _evGraphData

    private val _evTotalSalesData = MutableStateFlow<UIState<TotalSalesData>>(UIState.Loading)
    val evTotalSalesData: StateFlow<UIState<TotalSalesData>> get() = _evTotalSalesData

    private val _evTotalLastMonthSalesData = MutableStateFlow<UIState<TotalLastMonthSalesData>>(UIState.Loading)
    val evTotalLastMonthSalesData: StateFlow<UIState<TotalLastMonthSalesData>> get() = _evTotalLastMonthSalesData

    private val _evLastMonthSalesData = MutableStateFlow<UIState<LastMonthSalesData>>(UIState.Loading)
    val evLastMonthSalesData: StateFlow<UIState<LastMonthSalesData>> get() = _evLastMonthSalesData

    private val _evTotalChargeStationsData = MutableStateFlow<UIState<TotalChargeStations>>(UIState.Loading)
    val evTotalChargeStationsData: StateFlow<UIState<TotalChargeStations>> get() = _evTotalChargeStationsData

    private val _evAverageSupplyTariffData = MutableStateFlow<UIState<AverageSupplyTariff>>(UIState.Loading)
    val evAverageSupplyTariffData: StateFlow<UIState<AverageSupplyTariff>> get() = _evAverageSupplyTariffData

    private val _evManufacturersSalesData = MutableStateFlow<UIState<SalesManufacturers>>(UIState.Loading)
    val evManufacturersSalesData: StateFlow<UIState<SalesManufacturers>> get() = _evManufacturersSalesData

    init {
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = dataRepository.getEVSalesGraphDataResponse(Timeline.Yearly)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _evGraphData.value = UIState.Success(data)
                    } else {
                        _evGraphData.value = UIState.Error("No data available")
                    }
                } else {
                    _evGraphData.value = UIState.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _evGraphData.value = UIState.Error("Exception: ${e.message}")
            }
        }


        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = dataRepository.getEVTotalSalesDataResponse()
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _evTotalSalesData.value = UIState.Success(data)
                    } else {
                        _evTotalSalesData.value = UIState.Error("No data available")
                    }
                } else {
                    _evTotalSalesData.value = UIState.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _evTotalSalesData.value = UIState.Error("Exception: ${e.message}")
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = dataRepository.getEVTotalLastMonthSalesDataResponse()
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _evTotalLastMonthSalesData.value = UIState.Success(data)
                    } else {
                        _evTotalLastMonthSalesData.value = UIState.Error("No data available")
                    }
                } else {
                    _evTotalLastMonthSalesData.value = UIState.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _evTotalLastMonthSalesData.value = UIState.Error("Exception: ${e.message}")
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = dataRepository.getLastMonthSalesDataResponse()
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _evLastMonthSalesData.value = UIState.Success(data)
                    } else {
                        _evLastMonthSalesData.value = UIState.Error("No data available")
                    }
                } else {
                    _evLastMonthSalesData.value = UIState.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _evLastMonthSalesData.value = UIState.Error("Exception: ${e.message}")
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = dataRepository.getTotalChargeStationsDataResponse()
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _evTotalChargeStationsData.value = UIState.Success(data)
                    } else {
                        _evTotalChargeStationsData.value = UIState.Error("No data available")
                    }
                } else {
                    _evTotalChargeStationsData.value = UIState.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _evTotalChargeStationsData.value = UIState.Error("Exception: ${e.message}")
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = dataRepository.getSalesManufacturersDataResponse()
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _evManufacturersSalesData.value = UIState.Success(data)
                    } else {
                        _evManufacturersSalesData.value = UIState.Error("No data available")
                    }
                } else {
                    _evManufacturersSalesData.value = UIState.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _evManufacturersSalesData.value = UIState.Error("Exception: ${e.message}")
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = dataRepository.getAverageSupplyTariffDataResponse()
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        _evAverageSupplyTariffData.value = UIState.Success(data)
                    } else {
                        _evAverageSupplyTariffData.value = UIState.Error("No data available")
                    }
                } else {
                    _evAverageSupplyTariffData.value = UIState.Error("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _evAverageSupplyTariffData.value = UIState.Error("Exception: ${e.message}")
            }
        }
    }
}
