package com.sookmyung.umbrellafriend.domain.entity

data class Version(
    val androidVersion: AndroidVersion,
    val notificationTitle: String,
    val notificationContent: String
)
