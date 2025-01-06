package com.example.vehicle.model

import com.google.gson.annotations.SerializedName

data class DateLabel(
    @SerializedName("latest_month")
    val latestMonth: String
)