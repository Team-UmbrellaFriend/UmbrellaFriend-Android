package com.sookmyung.umbrellafriend.domain.usecase

import com.sookmyung.umbrellafriend.domain.repository.UsersRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PostJoinUseCase @Inject constructor(
    private val usersRepository: UsersRepository
){
    suspend operator fun invoke(
        studentCard:MultipartBody.Part,
        body: HashMap<String, RequestBody>
    ) = usersRepository.postJoin(studentCard, body)
}