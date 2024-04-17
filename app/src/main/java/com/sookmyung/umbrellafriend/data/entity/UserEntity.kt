package com.sookmyung.umbrellafriend.data.entity

import com.sookmyung.umbrellafriend.domain.entity.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
    @SerialName("id") val id: Int,
    @SerialName("username") val username: String
) {
    fun toUser(): User = User(
        id = this.id,
        username = this.username
    )
}