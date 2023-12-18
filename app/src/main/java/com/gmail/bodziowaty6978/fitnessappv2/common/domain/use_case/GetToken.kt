package com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton.TokenStatus
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource

class GetToken(
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(): String? {
        val resource = tokenRepository.getToken()
        return if (resource is Resource.Error) {
            TokenStatus.changeTokenStatus(false)
            null
        } else resource.data!!
    }
}