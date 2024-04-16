package com.sookmyung.umbrellafriend.data.repositoryImpl

import com.sookmyung.umbrellafriend.data.entity.request.LoginRequest
import com.sookmyung.umbrellafriend.data.source.LocalDataSource
import com.sookmyung.umbrellafriend.data.source.UsersDataSource
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


    override fun initToken(token: String) {
        localDataSource.token = token
    }

}