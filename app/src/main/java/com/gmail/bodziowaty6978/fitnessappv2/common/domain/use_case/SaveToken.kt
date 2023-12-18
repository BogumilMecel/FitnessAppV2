package com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton.TokenStatus
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult

class SaveToken(
    private val tokenRepository: TokenRepository
) {
    operator fun invoke(token:String):Boolean{
        return when(tokenRepository.saveToken(token)){
            is CustomResult.Success -> true
            is CustomResult.Error -> {
                TokenStatus.changeTokenStatus(false)
                false
            }
        }
    }
}