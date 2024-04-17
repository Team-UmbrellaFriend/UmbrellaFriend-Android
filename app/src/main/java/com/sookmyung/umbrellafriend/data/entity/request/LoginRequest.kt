package com.sookmyung.umbrellafriend.data.entity.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("studentID") val studentID: String,
    @SerialName("password") val password: String,
    @SerialName("fcm_token") val fcmToken: String
)
