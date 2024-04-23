package com.sookmyung.umbrellafriend.domain.usecase

import com.sookmyung.umbrellafriend.domain.repository.UsersRepository
import javax.inject.Inject

class GetLogoutUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke() = usersRepository.getLogout()
}