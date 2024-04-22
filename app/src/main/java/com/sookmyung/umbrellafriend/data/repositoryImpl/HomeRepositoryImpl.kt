package com.sookmyung.umbrellafriend.data.repositoryImpl

import com.sookmyung.umbrellafriend.data.source.HomeDataSource
import com.sookmyung.umbrellafriend.domain.entity.Home
import com.sookmyung.umbrellafriend.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource
) : HomeRepository {
    override suspend fun getHome(): Result<Home> = runCatching {
        homeDataSource.getHome()
    }.mapCatching { response -> requireNotNull(response.data).toHome() }
}