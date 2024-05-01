package com.sookmyung.umbrellafriend.domain.usecase

import com.sookmyung.umbrellafriend.data.entity.request.ReturnRequest
import com.sookmyung.umbrellafriend.domain.repository.UmbrellaRepository
import javax.inject.Inject

class PostUmbrellaReturnUseCase @Inject constructor(
    private val umbrellaRepository: UmbrellaRepository
) {
    suspend operator fun invoke(returnRequest: ReturnRequest) =
        umbrellaRepository.postUmbrellaReturn(returnRequest)
}