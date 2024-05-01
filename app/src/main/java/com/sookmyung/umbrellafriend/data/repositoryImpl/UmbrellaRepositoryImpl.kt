package com.sookmyung.umbrellafriend.data.repositoryImpl

import com.sookmyung.umbrellafriend.data.entity.request.ReturnRequest
import com.sookmyung.umbrellafriend.data.source.UmbrellaDataSource
import com.sookmyung.umbrellafriend.domain.entity.AvailableUmbrella
import com.sookmyung.umbrellafriend.domain.entity.UmbrellaRental
import com.sookmyung.umbrellafriend.domain.repository.UmbrellaRepository
import javax.inject.Inject

class UmbrellaRepositoryImpl @Inject constructor(
    private val umbrellaDataSource: UmbrellaDataSource
) : UmbrellaRepository {
    override suspend fun getExtend(): Result<Unit> = runCatching {
        umbrellaDataSource.getExtend()
    }

    override suspend fun getAvailableUmbrella(): Result<List<AvailableUmbrella>> = runCatching {
        umbrellaDataSource.getAvailableUmbrella()
    }.mapCatching { response -> requireNotNull(response.data).map { it.toAvailableUmbrella() } }

    override suspend fun getUmbrellaRental(umbrellaNumber: Int): Result<UmbrellaRental> =
        runCatching {
            umbrellaDataSource.getUmbrellaRental(umbrellaNumber)
        }.mapCatching { response -> requireNotNull(response.data).toUmbrellaRental() }

    override suspend fun postUmbrellaRental(umbrellaNumber: Int): Result<String> =
        runCatching {
            umbrellaDataSource.postUmbrellaRental(umbrellaNumber)
        }.mapCatching { response -> requireNotNull(response.data) }

    override suspend fun postUmbrellaReturn(returnRequest: ReturnRequest): Result<String> =
        runCatching {
            umbrellaDataSource.postUmbrellaReturn(returnRequest)
        }.mapCatching { response -> requireNotNull(response.data) }
}