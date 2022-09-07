package com.gmail.bodziowaty6978.fitnessappv2.common.domain.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource

interface TokenRepository {

    suspend fun getToken():Resource<String>

    suspend fun saveToken(token:String): CustomResult
}