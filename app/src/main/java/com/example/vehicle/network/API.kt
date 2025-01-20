package com.example.vehicle.network

import com.example.vehicle.model.avergaePowerSupplyTraiff.AverageSupplyTariff
import com.example.vehicle.model.evGraph.EvSalesGraphData
import com.example.vehicle.model.lastMonthSales.LastMonthSalesData
import com.example.vehicle.model.salesManufacturers.SalesManufacturers
import com.example.vehicle.model.totalChargeStations.TotalChargeStations
import com.example.vehicle.model.totalLastMonthSales.TotalLastMonthSalesData
import com.example.vehicle.model.totalSales.TotalSalesData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface API {

    @GET("research/ev-sales-graph?format=json")
    suspend fun getEVGraphData(
        @Query("timeline") timeline: String
    ): Response<EvSalesGraphData>

    @GET("research/sales-by-state?format=json&is_last_updated_month=false&stateid=1")
    suspend fun getEVTotalSalesData():
            Response<TotalSalesData>

    @GET("research/total-ev-sales-latest-month?format=json")
    suspend fun getTotalLastMonthSalesData():
            Response<TotalLastMonthSalesData>

    @GET("research/sales-by-state?format=json&is_last_updated_month=true&stateid=1")
    suspend fun getLastMonthSalesData():
            Response<LastMonthSalesData>

    @GET("research/energy-infrastructure/total-charging-stations?format=json")
    suspend fun getTotalChargeStationsData():
            Response<TotalChargeStations>

    @GET("research/energy-infrastructure/average-supply-tariff?format=json")
    suspend fun getAverageSupplyTariffData():
            Response<AverageSupplyTariff>

    @GET("list/sales-manufacturers?categoryid=1&format=json&month_year=MAR-2024&stateid=1")
    suspend fun getSalesManufacturersData():
            Response<SalesManufacturers>
}