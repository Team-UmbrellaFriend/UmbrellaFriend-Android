package com.sookmyung.umbrellafriend.domain.entity

data class Weather(
    val dateWeather: DateWeather = DateWeather(),
    val message: String = ""
)
