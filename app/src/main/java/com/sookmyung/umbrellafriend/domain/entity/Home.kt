package com.sookmyung.umbrellafriend.domain.entity

data class Home(
    val user: User,
    val weather: Weather,
    val dDay: DDay
)