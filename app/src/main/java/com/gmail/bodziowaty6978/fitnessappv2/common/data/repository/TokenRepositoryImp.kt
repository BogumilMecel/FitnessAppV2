package com.gmail.bodziowaty6978.fitnessappv2.common.data.repository

import android.content.SharedPreferences
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource

class TokenRepositoryImp(
    private val sharedPreferences: SharedPreferences,
):TokenRepository {

    override suspend fun getToken(): Resource<String> {
        val token = sharedPreferences.getString("token",null)
        return token?.let {
            Resource.Success(it)
        }?: Resource.Error(uiText = "")
    }

    override suspend fun saveToken(token: String): CustomResult {
        return try {
            sharedPreferences.edit().putString("token",token).apply()
            CustomResult.Success
        }catch (e:Exception){
            CustomResult.Error(e.message.toString())
        }

    }
}