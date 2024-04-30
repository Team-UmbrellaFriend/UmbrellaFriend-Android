package com.sookmyung.umbrellafriend.data.source

import com.sookmyung.umbrellafriend.data.entity.response.BaseResponse
import com.sookmyung.umbrellafriend.data.entity.response.HomeResponse
import com.sookmyung.umbrellafriend.data.entity.response.VersionResponse
import com.sookmyung.umbrellafriend.data.service.EtcService
import com.sookmyung.umbrellafriend.data.service.HomeService
import javax.inject.Inject

class EtcDataSource @Inject constructor(
    private val etcService: EtcService
) {
    suspend fun getVersion(): BaseResponse<VersionResponse> = etcService.getVersion()
}