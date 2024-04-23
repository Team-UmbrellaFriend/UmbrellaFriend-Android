package com.sookmyung.umbrellafriend.domain.repository

import com.sookmyung.umbrellafriend.data.entity.request.LoginRequest
import com.sookmyung.umbrellafriend.domain.entity.Mypage
import com.sookmyung.umbrellafriend.domain.entity.Token
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface MypageRepository {
    suspend fun getMypage(): Result<Mypage>
}