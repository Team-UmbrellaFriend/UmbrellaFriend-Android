package com.sookmyung.umbrellafriend.domain.usecase

import com.sookmyung.umbrellafriend.data.entity.request.LoginRequest
import com.sookmyung.umbrellafriend.domain.repository.UsersRepository
import javax.inject.Inject

class PostLoginUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(
        loginRequest: LoginRequest
    ) = usersRepository.postLogin(loginRequest)
}