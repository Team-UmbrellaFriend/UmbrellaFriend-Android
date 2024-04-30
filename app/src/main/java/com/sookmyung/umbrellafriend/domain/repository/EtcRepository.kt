package com.sookmyung.umbrellafriend.domain.repository

import com.sookmyung.umbrellafriend.domain.entity.Version

interface EtcRepository {
    suspend fun getVersion(): Result<Version>
}