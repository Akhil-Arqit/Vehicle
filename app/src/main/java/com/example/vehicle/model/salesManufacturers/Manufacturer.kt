package com.example.vehicle.model.salesManufacturers

data class Manufacturer(
    val id: Int,
    val manufacturer: String,
    val month_on_month: Double,
    val sales: Int,
    val year_on_year: Double
)