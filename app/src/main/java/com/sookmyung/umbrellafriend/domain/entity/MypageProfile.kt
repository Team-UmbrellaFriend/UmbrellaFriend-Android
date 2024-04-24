package com.sookmyung.umbrellafriend.domain.entity

data class MypageProfile(
    val id: Int,
    val username: String,
    val email: String,
    val profile: Profile,
)