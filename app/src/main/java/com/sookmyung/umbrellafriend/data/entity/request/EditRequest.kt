package com.sookmyung.umbrellafriend.data.entity.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EditRequest(
    @SerialName("username") val username: String,
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
    @SerialName("password2") val password2: String,
    @SerialName("profile.phoneNumber") val phoneNumber: String
)
