package com.sookmyung.umbrellafriend.data.entity

import com.sookmyung.umbrellafriend.domain.entity.History
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HistoryEntity(
    @SerialName("rental_period") val rentalPeriod: String,
    @SerialName("rent_date") val rentDate: String
) {
    fun toHistory(): History = History(
        rentalPeriod = this.rentalPeriod,
        rentDate = this.rentDate
    )
}