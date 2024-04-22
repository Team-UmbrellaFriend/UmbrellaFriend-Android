package com.sookmyung.umbrellafriend.domain.usecase

import com.sookmyung.umbrellafriend.domain.repository.UsersRepository
import javax.inject.Inject

class InitTokenUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    operator fun invoke(token: String) = usersRepository.initToken("Token $token")
}