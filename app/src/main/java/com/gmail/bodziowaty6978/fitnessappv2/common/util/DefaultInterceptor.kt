package com.gmail.bodziowaty6978.fitnessappv2.common.util

import android.content.SharedPreferences
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.Country
import okhttp3.Interceptor
import okhttp3.Response

class DefaultInterceptor(
    private val sharedPreferences: SharedPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        sharedPreferences.getString("token", null)?.let {
            request.addHeader("Authorization", "Bearer $it")
        }
        request.addHeader("country", Country.POLAND.shortName)
        return chain.proceed(request.build())
    }
}