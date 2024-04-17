package com.sookmyung.umbrellafriend.data.source

import com.sookmyung.umbrellafriend.data.entity.response.BaseResponse
import com.sookmyung.umbrellafriend.data.entity.response.HomeResponse
import com.sookmyung.umbrellafriend.data.service.HomeService
import javax.inject.Inject

class HomeDataSource @Inject constructor(
    private val homeService: HomeService
) {
    suspend fun getHome(): BaseResponse<HomeResponse> = homeService.getHome()
}