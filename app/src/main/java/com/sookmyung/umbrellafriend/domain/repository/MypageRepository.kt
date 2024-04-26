package com.sookmyung.umbrellafriend.domain.repository

import com.sookmyung.umbrellafriend.data.entity.request.ReportRequest
import com.sookmyung.umbrellafriend.domain.entity.Mypage

interface MypageRepository {
    suspend fun getMypage(): Result<Mypage>
    suspend fun postReport(reportRequest: ReportRequest): Result<String>
}