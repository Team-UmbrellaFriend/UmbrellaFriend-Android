package com.sookmyung.umbrellafriend.domain.repository

import com.sookmyung.umbrellafriend.domain.entity.Home

interface HomeRepository {
    suspend fun getHome(): Result<Home>
}