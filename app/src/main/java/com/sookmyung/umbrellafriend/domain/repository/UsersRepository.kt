package com.sookmyung.umbrellafriend.domain.repository

import com.sookmyung.umbrellafriend.data.entity.request.EditRequest
import com.sookmyung.umbrellafriend.data.entity.request.LoginRequest
import com.sookmyung.umbrellafriend.data.entity.request.WithdrawRequest
import com.sookmyung.umbrellafriend.domain.entity.MypageProfile
import com.sookmyung.umbrellafriend.domain.entity.Token
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface UsersRepository {
    suspend fun postJoin(
        studentCard: MultipartBody.Part,
        body: HashMap<String, RequestBody>
    ): Result<Token>

    suspend fun postLogin(
        loginRequest: LoginRequest
    ): Result<Token>

    suspend fun getUserProfile(userId: Int): Result<MypageProfile>
    suspend fun putUserProfile(
        userId: Int,
        editRequest: EditRequest
    ): Result<MypageProfile>

    suspend fun deleteWithdraw(withdrawRequest: WithdrawRequest): Result<String>

    suspend fun getLogout(): Result<Unit>

    fun initToken(token: String)
    fun setLogin(login: Boolean)
    fun getLogin(): Boolean
}