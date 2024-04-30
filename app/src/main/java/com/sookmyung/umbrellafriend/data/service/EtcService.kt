package com.sookmyung.umbrellafriend.data.service

import com.sookmyung.umbrellafriend.data.entity.response.BaseResponse
import com.sookmyung.umbrellafriend.data.entity.response.HomeResponse
import com.sookmyung.umbrellafriend.data.entity.response.VersionResponse
import retrofit2.http.GET

interface EtcService {
    @GET("api/version/info/")
    suspend fun getVersion(): BaseResponse<VersionResponse>
}