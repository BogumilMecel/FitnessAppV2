package com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton.TokenStatus
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource

class SaveToken(
    private val tokenRepository: TokenRepository
) {
    operator fun invoke(token:String):Boolean{
        return when(tokenRepository.saveToken(token)){
            is Resource.Success -> true
            is Resource.Error -> {
                TokenStatus.changeTokenStatus(false)
                false
            }
        }
    }
}