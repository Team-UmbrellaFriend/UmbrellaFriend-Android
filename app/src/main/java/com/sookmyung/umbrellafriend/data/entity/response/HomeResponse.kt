package com.sookmyung.umbrellafriend.data.entity.response

import com.sookmyung.umbrellafriend.data.entity.DDayEntity
import com.sookmyung.umbrellafriend.data.entity.UserEntity
import com.sookmyung.umbrellafriend.data.entity.WeatherEntity
import com.sookmyung.umbrellafriend.domain.entity.Home
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeResponse(
    @SerialName("user") val user: UserEntity,
    @SerialName("weather") val weather: WeatherEntity,
    @SerialName("d-day") val dDay: DDayEntity
) {
    fun toHome(): Home = Home(
        user = this.user.toUser(),
        weather = this.weather.toWeather(),
        dDay = this.dDay.toDDay()
    )
}