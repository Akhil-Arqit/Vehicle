package com.example.vehicle.repository

import com.example.vehicle.model.Timeline
import com.example.vehicle.network.RetrofitInstance


class DataRepository {
    suspend fun getResponse(timeline: Timeline) = RetrofitInstance.api.getResponse(timeline.value)
}
