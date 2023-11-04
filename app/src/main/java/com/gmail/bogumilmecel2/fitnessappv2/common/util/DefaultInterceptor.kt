package com.gmail.bogumilmecel2.fitnessappv2.common.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Country
import kotlinx.datetime.TimeZone
import okhttp3.Interceptor
import okhttp3.Response

class DefaultInterceptor(
    private val sharedPreferences: SharedPreferences,
    private val applicationContext: Context
) : Interceptor {
    @SuppressLint("HardwareIds")
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        sharedPreferences.getString("token", null)?.let {
            request.addHeader("Authorization", "Bearer $it")
        }
        request.addHeader(
            name = Constants.Headers.COUNTRY,
            value = Country.POLAND.shortName
        )
        request.addHeader(
            name = Constants.Headers.TIMEZONE,
            value = TimeZone.currentSystemDefault().id
        )
        request.addHeader(
            name = Constants.Headers.DEVICE_ID,
            value = Settings.Secure.getString(
                applicationContext.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        )

        val response = chain.proceed(request.build())

        return if (response.code == 204) {
            response.newBuilder().code(200).build()
        } else {
            response
        }
    }
}