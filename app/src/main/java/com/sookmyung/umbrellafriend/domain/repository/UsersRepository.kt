package com.sookmyung.umbrellafriend.domain.repository

import com.sookmyung.umbrellafriend.data.entity.response.TokenResponse
import com.sookmyung.umbrellafriend.domain.entity.Token
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface UsersRepository {
    suspend fun postJoin(
        studentCard: MultipartBody.Part,
        body: HashMap<String, RequestBody>
    ): Result<Token>

    fun initToken(token: String)
}