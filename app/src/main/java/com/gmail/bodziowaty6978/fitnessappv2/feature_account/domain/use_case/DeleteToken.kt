package com.gmail.bodziowaty6978.fitnessappv2.feature_account.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource

class DeleteToken(
    private val tokenRepository: TokenRepository
) {
    operator fun invoke(): Resource<Unit> {
        return tokenRepository.deleteToken()
    }
}