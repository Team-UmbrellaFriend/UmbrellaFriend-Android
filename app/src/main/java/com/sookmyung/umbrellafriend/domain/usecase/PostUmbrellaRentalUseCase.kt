package com.sookmyung.umbrellafriend.domain.usecase

import com.sookmyung.umbrellafriend.domain.repository.UmbrellaRepository
import javax.inject.Inject

class PostUmbrellaRentalUseCase @Inject constructor(
    private val umbrellaRepository: UmbrellaRepository
) {
    suspend operator fun invoke(umbrellaNumber: Int) =
        umbrellaRepository.postUmbrellaRental(umbrellaNumber)
}