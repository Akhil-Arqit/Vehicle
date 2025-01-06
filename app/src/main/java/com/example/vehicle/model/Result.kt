package com.example.vehicle.model

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("commercial_vehicle_million") val commercialVehicleMillion: Double,
    @SerializedName("passenger_vehicle_million") val passengerVehicleMillion: Double,
    @SerializedName("three_wheeler_million") val threeWheelerMillion: Double,
    @SerializedName("total_million") val totalMillion: Double,
    @SerializedName("two_wheeler_million") val twoWheelerMillion: Double,
    @SerializedName("year") val year: String
)