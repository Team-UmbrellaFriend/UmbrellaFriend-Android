package com.sookmyung.umbrellafriend.data.source

import com.sookmyung.umbrellafriend.data.entity.request.ReportRequest
import com.sookmyung.umbrellafriend.data.entity.response.BaseResponse
import com.sookmyung.umbrellafriend.data.entity.response.MypageResponse
import com.sookmyung.umbrellafriend.data.service.MypageService
import javax.inject.Inject

class MypageDataSource @Inject constructor(
    private val mypageService: MypageService
) {
    suspend fun getMypage(): BaseResponse<MypageResponse> = mypageService.getMypage()
    suspend fun postReport(reportRequest: ReportRequest): BaseResponse<String> =
        mypageService.postReport(reportRequest)
}