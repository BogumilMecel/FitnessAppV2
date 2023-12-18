package com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource

class GetToken(private val tokenRepository: TokenRepository) {
    suspend operator fun invoke(): String? {
        return when (val resource = tokenRepository.getToken()) {
            is Resource.Error -> {
                null
            }

            is Resource.Success -> {
                "Bearer ${resource.data}"
            }
        }
    }
}