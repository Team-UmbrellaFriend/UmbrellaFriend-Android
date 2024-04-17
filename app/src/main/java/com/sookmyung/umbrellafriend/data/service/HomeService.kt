package com.sookmyung.umbrellafriend.data.service

import com.sookmyung.umbrellafriend.data.entity.response.BaseResponse
import com.sookmyung.umbrellafriend.data.entity.response.HomeResponse
import retrofit2.http.GET

interface HomeService {
    @GET("home/")
    suspend fun getHome(): BaseResponse<HomeResponse>
}