package com.sookmyung.umbrellafriend.domain.usecase

import com.sookmyung.umbrellafriend.domain.repository.MypageRepository
import javax.inject.Inject

class GetMypageUseCase @Inject constructor(
    private val mypageRepository: MypageRepository
) {
    suspend operator fun invoke() = mypageRepository.getMypage()
}