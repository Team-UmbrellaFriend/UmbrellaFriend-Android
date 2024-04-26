package com.sookmyung.umbrellafriend.data.service

import com.sookmyung.umbrellafriend.data.entity.request.ReportRequest
import com.sookmyung.umbrellafriend.data.entity.response.BaseResponse
import com.sookmyung.umbrellafriend.data.entity.response.MypageResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MypageService {
    @GET("mypage/")
    suspend fun getMypage(): BaseResponse<MypageResponse>

    @POST("mypage/report/")
    suspend fun postReport(@Body reportRequest: ReportRequest): BaseResponse<String>
}