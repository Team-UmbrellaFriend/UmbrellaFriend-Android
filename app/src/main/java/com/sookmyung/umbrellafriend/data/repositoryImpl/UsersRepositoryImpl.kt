package com.sookmyung.umbrellafriend.data.repositoryImpl

import com.sookmyung.umbrellafriend.data.entity.request.EditRequest
import com.sookmyung.umbrellafriend.data.entity.request.LoginRequest
import com.sookmyung.umbrellafriend.data.entity.request.WithdrawRequest
import com.sookmyung.umbrellafriend.data.source.LocalDataSource
import com.sookmyung.umbrellafriend.data.source.UsersDataSource
import com.sookmyung.umbrellafriend.domain.entity.MypageProfile
import com.sookmyung.umbrellafriend.domain.entity.Token
import com.sookmyung.umbrellafriend.domain.repository.UsersRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersDataSource: UsersDataSource,
    private val localDataSource: LocalDataSource
) : UsersRepository {
    override suspend fun postJoin(
        studentCard: MultipartBody.Part,
        body: HashMap<String, RequestBody>
    ): Result<Token> = runCatching {
        usersDataSource.postJoin(studentCard, body)
    }.map { response -> requireNotNull(response.data).toToken() }

    override suspend fun postLogin(loginRequest: LoginRequest): Result<Token> = runCatching {
        usersDataSource.postLogin(loginRequest)
    }.map { response -> requireNotNull(response.data).toToken() }

    override suspend fun getUserProfile(userId: Int): Result<MypageProfile> = runCatching {
        usersDataSource.getUserProfile(userId)
    }.map { response -> requireNotNull(response.data).toMypageProfile() }

    override suspend fun putUserProfile(
        userId: Int,
        editRequest: EditRequest
    ): Result<MypageProfile> = runCatching {
        usersDataSource.putUserProfile(userId, editRequest)
    }.map { response -> requireNotNull(response.data).toMypageProfile() }

    override suspend fun getLogout(): Result<Unit> = runCatching {
        localDataSource.login = false
        localDataSource.token = ""
        usersDataSource.getLogout()
    }

    override suspend fun deleteWithdraw(withdrawRequest: WithdrawRequest): Result<String> =
        runCatching {
            usersDataSource.deleteWithdraw(withdrawRequest)
        }.mapCatching { response -> requireNotNull(response.data) }

    override fun initToken(token: String) {
        localDataSource.token = token
    }

    override fun setLogin(login: Boolean) {
        localDataSource.login = login
    }

    override fun getLogin() = localDataSource.login
}