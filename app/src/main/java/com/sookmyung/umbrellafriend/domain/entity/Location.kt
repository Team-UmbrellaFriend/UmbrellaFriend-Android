package com.sookmyung.umbrellafriend.domain.entity

import android.net.Uri

data class Location(
    val locationName: String,
    val locationEngName: String,
    val imageUri: Uri
)
