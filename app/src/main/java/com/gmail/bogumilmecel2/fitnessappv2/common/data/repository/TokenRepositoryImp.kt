package com.gmail.bogumilmecel2.fitnessappv2.common.data.repository

import android.content.SharedPreferences
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.RealResourceProvider

class TokenRepositoryImp(
    private val sharedPreferences: SharedPreferences,
    realResourceProvider: RealResourceProvider
) : TokenRepository, BaseRepository(realResourceProvider) {
    override suspend fun getToken(): Resource<String> {
        return handleRequest {
            sharedPreferences.getString("token", null) ?: throw NullPointerException()
        }
    }

    override suspend fun saveToken(token: String): Resource<Unit> {
        return handleRequest {
            sharedPreferences.edit().putString("token", token).apply()
        }
    }

    override suspend fun deleteToken(): Resource<Unit> {
        return handleRequest {
            sharedPreferences.edit().remove("token").apply()
        }
    }
}