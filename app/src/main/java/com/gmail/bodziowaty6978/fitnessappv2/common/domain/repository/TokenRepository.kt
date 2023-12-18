package com.gmail.bodziowaty6978.fitnessappv2.common.domain.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource

interface TokenRepository {
    fun getToken():Resource<String>
    fun saveToken(token:String): CustomResult
    fun deleteToken(): CustomResult
}