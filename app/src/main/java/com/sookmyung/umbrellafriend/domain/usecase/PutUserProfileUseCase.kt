package com.sookmyung.umbrellafriend.domain.usecase

import com.sookmyung.umbrellafriend.data.entity.request.EditRequest
import com.sookmyung.umbrellafriend.domain.repository.UsersRepository
import okhttp3.RequestBody
import javax.inject.Inject

class PutUserProfileUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(
        userId: Int,
        editRequest: EditRequest
    ) = usersRepository.putUserProfile(userId, editRequest)
}