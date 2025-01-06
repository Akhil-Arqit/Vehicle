package com.example.vehicle.network

import com.example.vehicle.model.VehicleData
import retrofit2.Response
import retrofit2.http.GET


interface API {

    @GET("explore/v2.1/catalog/datasets/india-vehicle-sales-trends/records")
    suspend fun getResponse() : Response<VehicleData>
}