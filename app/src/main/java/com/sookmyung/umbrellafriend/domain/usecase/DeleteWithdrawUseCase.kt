package com.sookmyung.umbrellafriend.domain.usecase

import com.sookmyung.umbrellafriend.data.entity.request.WithdrawRequest
import com.sookmyung.umbrellafriend.domain.repository.UsersRepository
import javax.inject.Inject

class DeleteWithdrawUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(
        withdrawRequest: WithdrawRequest
    ) = usersRepository.deleteWithdraw(withdrawRequest)
}