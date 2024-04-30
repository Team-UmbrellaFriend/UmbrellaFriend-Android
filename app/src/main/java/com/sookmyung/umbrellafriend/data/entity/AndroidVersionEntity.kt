package com.sookmyung.umbrellafriend.data.entity

import com.sookmyung.umbrellafriend.domain.entity.AndroidVersion
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AndroidVersionEntity(
    @SerialName("appVersion") val appVersion: String,
    @SerialName("forceUpdateVersion") val forceUpdateVersion: String
) {
    fun toAndroidVersion(): AndroidVersion = AndroidVersion(
        appVersion = this.appVersion,
        forceUpdateVersion = this.forceUpdateVersion
    )
}