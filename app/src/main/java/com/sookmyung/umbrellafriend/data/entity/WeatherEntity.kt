package com.sookmyung.umbrellafriend.data.entity

import com.sookmyung.umbrellafriend.domain.entity.Weather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherEntity(
    @SerialName("weather") val dateWeather: DateWeatherEntity,
    @SerialName("message") val message: String
) {
    fun toWeather(): Weather = Weather(
        dateWeather = this.dateWeather.toDateWeather(),
        message = this.message
    )
}