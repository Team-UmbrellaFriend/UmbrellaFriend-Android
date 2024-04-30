package com.sookmyung.umbrellafriend.data.service

import com.sookmyung.umbrellafriend.data.entity.response.AvailableUmbrellaResponse
import com.sookmyung.umbrellafriend.data.entity.response.BaseResponse
import com.sookmyung.umbrellafriend.data.entity.response.UmbrellaRentalResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UmbrellaService {
    @GET("umbrella/extend/")
    suspend fun getExtend(): BaseResponse<Unit>

    @GET("umbrella/available/")
    suspend fun getAvailableUmbrella(): BaseResponse<List<AvailableUmbrellaResponse>>

    @GET("/umbrella/{umbrella_number}/check/")
    suspend fun getUmbrellaRental(
        @Path("umbrella_number") umbrellaNumber: Int
    ): BaseResponse<UmbrellaRentalResponse>
}