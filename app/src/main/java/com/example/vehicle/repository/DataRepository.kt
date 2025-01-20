package com.example.vehicle.repository

import com.example.vehicle.model.Timeline
import com.example.vehicle.network.RetrofitInstance


class DataRepository {
    suspend fun getEVSalesGraphDataResponse(timeline: Timeline) = RetrofitInstance.api.getEVGraphData(timeline.value)
    suspend fun getEVTotalSalesDataResponse() = RetrofitInstance.api.getEVTotalSalesData()
    suspend fun getEVTotalLastMonthSalesDataResponse() = RetrofitInstance.api.getTotalLastMonthSalesData()
    suspend fun getLastMonthSalesDataResponse() = RetrofitInstance.api.getLastMonthSalesData()
    suspend fun getTotalChargeStationsDataResponse() = RetrofitInstance.api.getTotalChargeStationsData()
    suspend fun getAverageSupplyTariffDataResponse() = RetrofitInstance.api.getAverageSupplyTariffData()
    suspend fun getSalesManufacturersDataResponse() = RetrofitInstance.api.getSalesManufacturersData()
}
