package com.sookmyung.umbrellafriend.data.entity.response

import com.sookmyung.umbrellafriend.data.entity.ProfileEntity
import com.sookmyung.umbrellafriend.domain.entity.MypageProfile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MypageProfileResponse(
    @SerialName("id") val id: Int,
    @SerialName("username") val username: String,
    @SerialName("email") val email: String,
    @SerialName("profile") val profile: ProfileEntity,
) {
    fun toMypageProfile(): MypageProfile = MypageProfile(
        id = this.id,
        username = this.username,
        email = this.email,
        profile = this.profile.toProfile()
    )
}