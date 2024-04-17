package com.sookmyung.umbrellafriend.data.entity

import com.sookmyung.umbrellafriend.domain.entity.DateWeather
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DateWeatherEntity(
    @SerialName("date") val date: String,
    @SerialName("percent") val percent: String
) {
    fun toDateWeather(): DateWeather = DateWeather(
        date = this.date,
        percent = this.percent
    )
}