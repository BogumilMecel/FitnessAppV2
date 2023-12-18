package com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case

import android.util.Log
import com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton.TokenStatus
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.util.TAG

class GetToken(
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(): String? {
        val resource = tokenRepository.getToken()
        return if (resource is Resource.Error) {
            TokenStatus.changeTokenStatus(false)
            null
        } else {
            Log.e(TAG,resource.data.toString())
            resource.data ?: kotlin.run {
                TokenStatus.changeTokenStatus(false)
                null
            }
        }
    }
}