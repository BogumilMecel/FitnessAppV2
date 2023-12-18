package com.gmail.bodziowaty6978.fitnessappv2.common.data.repository

import android.content.SharedPreferences
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider

class TokenRepositoryImp(
    private val sharedPreferences: SharedPreferences,
    private val resourceProvider: ResourceProvider
) : TokenRepository, BaseRepository(resourceProvider) {
    override fun getToken(): Resource<String> {
        val token = sharedPreferences.getString("token", null)
        return token?.let {
            Resource.Success(it)
        } ?: Resource.Error(uiText = "")
    }

    override fun saveToken(token: String): CustomResult {
        return try {
            sharedPreferences.edit().putString("token", token).apply()
            CustomResult.Success
        } catch (e: Exception) {
            handleExceptionWithCustomResult(exception = e)
        }
    }

    override fun deleteToken(): CustomResult {
        return try {
            sharedPreferences.edit().remove("token")
            CustomResult.Success
        } catch (e: Exception) {
            handleExceptionWithCustomResult(exception = e)
        }
    }
}