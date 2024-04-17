package com.sookmyung.umbrellafriend.data.entity

import com.sookmyung.umbrellafriend.domain.entity.DDay
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DDayEntity(
    @SerialName("is_overdue") val isOverDue: Boolean,
    @SerialName("overdue_days") val overdueDays: Int,
    @SerialName("days_remaining") val daysRemaining: Int
) {
    fun toDDay(): DDay = DDay(
        isOverDue = this.isOverDue,
        overdueDays = this.overdueDays,
        daysRemaining = this.daysRemaining
    )
}