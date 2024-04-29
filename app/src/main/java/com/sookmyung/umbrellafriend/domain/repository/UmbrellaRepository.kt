package com.sookmyung.umbrellafriend.domain.repository

import com.sookmyung.umbrellafriend.domain.entity.AvailableUmbrella

interface UmbrellaRepository {
    suspend fun getExtend(): Result<Unit>

    suspend fun getAvailableUmbrella(): Result<AvailableUmbrella>
}