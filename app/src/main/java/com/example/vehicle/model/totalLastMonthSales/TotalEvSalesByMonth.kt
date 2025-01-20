package com.example.vehicle.model.totalLastMonthSales

data class TotalEvSalesByMonth(
    val date_label: String,
    val month_on_month: Double,
    val total_count: Int
)