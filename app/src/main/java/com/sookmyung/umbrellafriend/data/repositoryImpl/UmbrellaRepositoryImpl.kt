package com.sookmyung.umbrellafriend.data.repositoryImpl

import com.sookmyung.umbrellafriend.data.source.UmbrellaDataSource
import com.sookmyung.umbrellafriend.domain.entity.AvailableUmbrella
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
}