package com.sookmyung.umbrellafriend.domain.usecase

import com.sookmyung.umbrellafriend.data.entity.request.ReportRequest
import com.sookmyung.umbrellafriend.domain.repository.MypageRepository
import javax.inject.Inject

class PostReportUseCase @Inject constructor(
    private val mypageRepository: MypageRepository
) {
    suspend operator fun invoke(
        reportRequest: ReportRequest
    ) = mypageRepository.postReport(reportRequest)
}