package com.sookmyung.umbrellafriend.domain.usecase

import com.sookmyung.umbrellafriend.domain.repository.EtcRepository
import javax.inject.Inject

class GetVersionUseCase @Inject constructor(
    private val etcRepository: EtcRepository
) {
    suspend operator fun invoke() = etcRepository.getVersion()
}