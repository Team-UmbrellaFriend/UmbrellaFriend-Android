package com.sookmyung.umbrellafriend.data.repositoryImpl

import com.sookmyung.umbrellafriend.data.source.EtcDataSource
import com.sookmyung.umbrellafriend.domain.entity.Version
import com.sookmyung.umbrellafriend.domain.repository.EtcRepository
import javax.inject.Inject

class EtcRepositoryImpl @Inject constructor(
    private val etcDataSource: EtcDataSource
) : EtcRepository {
    override suspend fun getVersion(): Result<Version> = runCatching {
        etcDataSource.getVersion()
    }.mapCatching { response -> requireNotNull(response.data).toVersion() }
}