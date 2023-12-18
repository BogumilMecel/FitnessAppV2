package com.gmail.bogumilmecel2.fitnessappv2.feature_account.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource

class DeleteTokenUseCase(private val tokenRepository: TokenRepository) {
    suspend operator fun invoke(): Resource<Unit> {
        return tokenRepository.deleteToken()
    }
}