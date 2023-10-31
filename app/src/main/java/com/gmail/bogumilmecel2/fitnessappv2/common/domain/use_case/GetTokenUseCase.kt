package com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.repository.TokenRepository

class GetTokenUseCase(private val tokenRepository: TokenRepository) {
    suspend operator fun invoke(): String? {
        return tokenRepository.getToken().data?.let { "Bearer $it" }
    }
}