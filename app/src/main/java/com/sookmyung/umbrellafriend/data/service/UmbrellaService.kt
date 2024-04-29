package com.sookmyung.umbrellafriend.data.service

import com.sookmyung.umbrellafriend.data.entity.response.AvailableUmbrellaResponse
import com.sookmyung.umbrellafriend.data.entity.response.BaseResponse
import retrofit2.http.GET

interface UmbrellaService {
    @GET("umbrella/extend/")
    suspend fun getExtend(): BaseResponse<Unit>

    @GET("umbrella/available/")
    suspend fun getAvailableUmbrella(): BaseResponse<AvailableUmbrellaResponse>
}