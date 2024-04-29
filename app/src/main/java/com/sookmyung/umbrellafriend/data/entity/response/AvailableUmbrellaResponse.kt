package com.sookmyung.umbrellafriend.data.entity.response

import com.sookmyung.umbrellafriend.domain.entity.AvailableUmbrella
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvailableUmbrellaResponse(
    @SerialName("location_id") val locationId: Int,
    @SerialName("location_name") val locationName: String,
    @SerialName("location_detail") val locationDetail: String,
    @SerialName("num_umbrellas") val numUmbrellas: Int
) {
    fun toAvailableUmbrella(): AvailableUmbrella = AvailableUmbrella(
        locationId = this.locationId,
        locationName = this.locationName,
        locationDetail = this.locationDetail,
        numUmbrellas = this.numUmbrellas
    )
}