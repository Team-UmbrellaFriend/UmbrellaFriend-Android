package com.sookmyung.umbrellafriend.data.source

import com.sookmyung.umbrellafriend.data.entity.response.AvailableUmbrellaResponse
import com.sookmyung.umbrellafriend.data.entity.response.BaseResponse
import com.sookmyung.umbrellafriend.data.entity.response.UmbrellaRentalResponse
import com.sookmyung.umbrellafriend.data.service.UmbrellaService
import javax.inject.Inject

class UmbrellaDataSource @Inject constructor(
    private val umbrellaService: UmbrellaService
) {
    suspend fun getExtend(): BaseResponse<Unit> = umbrellaService.getExtend()

    suspend fun getAvailableUmbrella(): BaseResponse<List<AvailableUmbrellaResponse>> =
        umbrellaService.getAvailableUmbrella()


    suspend fun getUmbrellaRental(umbrellaNumber: Int): BaseResponse<UmbrellaRentalResponse> =
        umbrellaService.getUmbrellaRental(umbrellaNumber)

    suspend fun postUmbrellaRental(umbrellaNumber: Int): BaseResponse<String> =
        umbrellaService.postUmbrellaRental(umbrellaNumber)
}