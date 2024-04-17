package com.sookmyung.umbrellafriend.domain.usecase

import com.sookmyung.umbrellafriend.domain.repository.UsersRepository
import javax.inject.Inject

class GetLoginUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    operator fun invoke() = usersRepository.getLogin()
}