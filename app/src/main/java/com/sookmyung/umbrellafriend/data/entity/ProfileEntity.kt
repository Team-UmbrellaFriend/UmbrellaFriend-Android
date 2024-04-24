package com.sookmyung.umbrellafriend.data.entity

import com.sookmyung.umbrellafriend.domain.entity.Profile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileEntity(
    @SerialName("studentID") val studentID: Int,
    @SerialName("phoneNumber") val phoneNumber: String
) {
    fun toProfile(): Profile = Profile(
        studentID = this.studentID,
        phoneNumber = this.phoneNumber
    )
}