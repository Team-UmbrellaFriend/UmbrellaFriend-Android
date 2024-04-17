package com.sookmyung.umbrellafriend.domain.entity

data class DDay(
    val isOverDue: Boolean,
    val overdueDays: Int,
    val daysRemaining: Int
)
