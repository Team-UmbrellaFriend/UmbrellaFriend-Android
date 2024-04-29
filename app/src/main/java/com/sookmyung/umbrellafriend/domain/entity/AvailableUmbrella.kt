package com.sookmyung.umbrellafriend.domain.entity

data class AvailableUmbrella(
    val locationId: Int,
    val locationName: String,
    val locationDetail: String,
    val numUmbrellas: Int
)