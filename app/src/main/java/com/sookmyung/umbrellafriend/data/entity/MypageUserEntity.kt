package com.sookmyung.umbrellafriend.data.entity

import com.sookmyung.umbrellafriend.domain.entity.MypageUser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MypageUserEntity(
    @SerialName("id") val id: Int,
    @SerialName("username") val username: String,
    @SerialName("studentID") val studentID: Int,
    @SerialName("phoneNumber") val phoneNumber: String,
    @SerialName("email") val email: String,
) {
    fun toMypageUser(): MypageUser = MypageUser(
        id = this.id,
        username = this.username,
        studentID = this.studentID.toString(),
        phoneNumber = this.phoneNumber,
        email = this.email
    )
}