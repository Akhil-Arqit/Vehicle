package com.example.vehicle.network

import com.example.vehicle.model.EvSalesGraphData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface API {

    @GET("research/ev-sales-graph?format=json")
    suspend fun getResponse(
        @Query("timeline") timeline: String
    ): Response<EvSalesGraphData>
}