package com.example.vehicle.repository

import com.example.vehicle.network.RetrofitInstance


class DataRepository {
    suspend fun getResponse() = RetrofitInstance.api.getResponse()
}
