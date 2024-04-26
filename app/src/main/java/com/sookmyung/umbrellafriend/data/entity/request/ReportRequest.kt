package com.sookmyung.umbrellafriend.data.entity.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportRequest(
    @SerialName("umbrella_number") val umbrellaNumber: String,
    @SerialName("report_reason") val reportReason: String,
    @SerialName("description") val description: String
)
