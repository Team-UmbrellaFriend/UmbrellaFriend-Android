package com.sookmyung.umbrellafriend.domain.entity

data class DDay(
    val isOverDue: Boolean = false,
    val overdueDays: Int = 0,
    val daysRemaining: Int = 0
)
