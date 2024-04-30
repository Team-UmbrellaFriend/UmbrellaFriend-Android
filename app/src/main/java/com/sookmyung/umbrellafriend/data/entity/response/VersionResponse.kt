package com.sookmyung.umbrellafriend.data.entity.response

import com.sookmyung.umbrellafriend.data.entity.AndroidVersionEntity
import com.sookmyung.umbrellafriend.domain.entity.Version
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VersionResponse(
    @SerialName("androidVersion") val androidVersion: AndroidVersionEntity,
    @SerialName("notificationTitle") val notificationTitle: String,
    @SerialName("notificationContent") val notificationContent: String
) {
    fun toVersion(): Version = Version(
        androidVersion = this.androidVersion.toAndroidVersion(),
        notificationTitle = this.notificationTitle,
        notificationContent = this.notificationContent
    )
}