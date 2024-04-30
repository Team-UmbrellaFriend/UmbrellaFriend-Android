package com.sookmyung.umbrellafriend.data.entity.response

import com.sookmyung.umbrellafriend.domain.entity.UmbrellaRental
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UmbrellaRentalResponse(
    @SerialName("umbrella_num") val umbrellaNum: Int,
    @SerialName("username") val username: String,
    @SerialName("studentID") val studentID: Int,
    @SerialName("date") val date: String,
) {
    fun toUmbrellaRental(): UmbrellaRental = UmbrellaRental(
        umbrellaNum = this.umbrellaNum,
        username = this.username,
        studentID = this.studentID,
        date = this.date
    )
}