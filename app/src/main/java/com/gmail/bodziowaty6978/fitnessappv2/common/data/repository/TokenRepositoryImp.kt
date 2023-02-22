package com.gmail.bodziowaty6978.fitnessappv2.common.data.repository

import android.content.SharedPreferences
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider

class TokenRepositoryImp(
    private val sharedPreferences: SharedPreferences,
    resourceProvider: ResourceProvider
) : TokenRepository, BaseRepository(resourceProvider) {
    override fun getToken(): Resource<String> {
        return handleRequest {
            sharedPreferences.getString("token", null) ?: throw NullPointerException()
        }
    }

    override fun saveToken(token: String): Resource<Unit> {
        return handleRequest {
            sharedPreferences.edit().putString("token", token).apply()
        }
    }

    override fun deleteToken(): Resource<Unit> {
        return handleRequest {
            sharedPreferences.edit().remove("token").apply()
        }
    }
}