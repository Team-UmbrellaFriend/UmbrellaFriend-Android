package com.sookmyung.umbrellafriend.domain.repository

import com.sookmyung.umbrellafriend.data.entity.request.ReturnRequest
import com.sookmyung.umbrellafriend.domain.entity.AvailableUmbrella
import com.sookmyung.umbrellafriend.domain.entity.UmbrellaRental

interface UmbrellaRepository {
    suspend fun getExtend(): Result<Unit>

    suspend fun getAvailableUmbrella(): Result<List<AvailableUmbrella>>
    suspend fun getUmbrellaRental(umbrellaNumber: Int): Result<UmbrellaRental>
    suspend fun postUmbrellaRental(umbrellaNumber: Int): Result<String>
    suspend fun postUmbrellaReturn(returnRequest: ReturnRequest): Result<String>
}