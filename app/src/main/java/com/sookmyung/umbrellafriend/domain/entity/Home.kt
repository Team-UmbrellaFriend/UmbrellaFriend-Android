package com.sookmyung.umbrellafriend.domain.entity

data class Home(
    val user: User = User(),
    val weather: Weather = Weather(),
    val dDay: DDay = DDay()
)