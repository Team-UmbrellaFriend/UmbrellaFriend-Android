package com.sookmyung.umbrellafriend.domain.entity

data class Mypage(
    val user: MypageUser,
    val history: List<History>
)