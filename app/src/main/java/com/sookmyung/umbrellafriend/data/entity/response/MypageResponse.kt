package com.sookmyung.umbrellafriend.data.entity.response

import com.sookmyung.umbrellafriend.data.entity.HistoryEntity
import com.sookmyung.umbrellafriend.data.entity.MypageUserEntity
import com.sookmyung.umbrellafriend.domain.entity.Mypage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MypageResponse(
    @SerialName("user") val user: MypageUserEntity,
    @SerialName("history") val history: List<HistoryEntity>
) {
    fun toMypage(): Mypage = Mypage(
        user = this.user.toMypageUser(),
        history = this.history.map { it.toHistory() }
    )
}