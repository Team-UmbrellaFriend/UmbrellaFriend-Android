package com.sookmyung.umbrellafriend.data.source

import com.sookmyung.umbrellafriend.data.entity.response.BaseResponse
import com.sookmyung.umbrellafriend.data.entity.response.HomeResponse
import com.sookmyung.umbrellafriend.data.service.HomeService
import com.sookmyung.umbrellafriend.data.service.UmbrellaService
import javax.inject.Inject

class UmbrellaDataSource @Inject constructor(
    private val umbrellaService: UmbrellaService
) {
    suspend fun getExtend(): BaseResponse<Unit> = umbrellaService.getExtend()
}