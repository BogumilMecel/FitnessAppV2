package com.gmail.bodziowaty6978.fitnessappv2.feature_account.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult

class DeleteToken(
    private val tokenRepository: TokenRepository
) {
    operator fun invoke(): CustomResult{
        return tokenRepository.deleteToken()
    }
}