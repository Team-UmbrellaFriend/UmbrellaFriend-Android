package com.sookmyung.umbrellafriend.data.entity.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WithdrawRequest(
    @SerialName("check") val check: Boolean,
    @SerialName("withdrawal_reason") val withdrawalReason: String,
    @SerialName("description") val description: String
)
