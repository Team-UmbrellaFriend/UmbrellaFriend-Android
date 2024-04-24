package com.sookmyung.umbrellafriend.domain.repository

import com.sookmyung.umbrellafriend.domain.entity.Home

interface UmbrellaRepository {
    suspend fun getExtend(): Result<Unit>
}