package com.example.vehicle.model

import com.google.gson.annotations.SerializedName

data class VehicleData(
    val results: List<Result>,
    @SerializedName("total_count") val totalCount: Int
)