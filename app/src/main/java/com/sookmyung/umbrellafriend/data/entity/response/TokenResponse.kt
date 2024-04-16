package com.sookmyung.umbrellafriend.data.entity.response

import com.sookmyung.umbrellafriend.domain.entity.Token
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    @SerialName("token") val token: String
) {
    fun toToken(): Token = Token(
        token = this.token
    )
}
