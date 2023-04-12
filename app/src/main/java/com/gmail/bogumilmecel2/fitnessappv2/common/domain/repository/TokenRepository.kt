package com.gmail.bogumilmecel2.fitnessappv2.common.domain.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource

interface TokenRepository {
    suspend fun getToken():Resource<String>
    suspend fun saveToken(token:String): Resource<Unit>
    suspend fun deleteToken(): Resource<Unit>
}