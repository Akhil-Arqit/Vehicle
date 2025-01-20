package com.example.vehicle.model.lastMonthSales

data class Data(
    val bus: Bus,
    val car_suv: CarSuv,
    val date_label: String,
    val three_wheeler_g: ThreeWheelerG,
    val three_wheeler_p: ThreeWheelerP,
    val total_ev_adoption: Double,
    val total_ev_sold: Int,
    val two_wheeler: TwoWheeler
)