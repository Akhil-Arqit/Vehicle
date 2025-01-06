package com.example.vehicle.model

import com.google.gson.annotations.SerializedName

data class EvSalesGraphData(
    val data: List<Data>,
    @SerializedName("date_label")
    val dateLabel: DateLabel
)