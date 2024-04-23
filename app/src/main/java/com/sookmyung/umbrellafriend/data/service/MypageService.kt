package com.sookmyung.umbrellafriend.data.service

import com.sookmyung.umbrellafriend.data.entity.response.BaseResponse
import com.sookmyung.umbrellafriend.data.entity.response.HomeResponse
import com.sookmyung.umbrellafriend.data.entity.response.MypageResponse
import retrofit2.http.GET

interface MypageService {
    @GET("mypage/")
    suspend fun getMypage(): BaseResponse<MypageResponse>
}