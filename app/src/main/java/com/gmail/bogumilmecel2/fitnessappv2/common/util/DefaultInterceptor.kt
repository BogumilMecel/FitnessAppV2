package com.gmail.bogumilmecel2.fitnessappv2.common.util

import android.content.SharedPreferences
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Country
import kotlinx.datetime.TimeZone
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
        request.addHeader(ApiConstants.Headers.COUNTRY, Country.POLAND.shortName)
        request.addHeader(ApiConstants.Headers.TIMEZONE, TimeZone.currentSystemDefault().id)

        val response = chain.proceed(request.build())

        return if (response.code == 204) {
            response.newBuilder().code(200).build()
        } else {
            response
        }
    }
}