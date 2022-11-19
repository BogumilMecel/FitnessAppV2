package com.gmail.bodziowaty6978.fitnessappv2.common.util

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.GetToken
import okhttp3.Interceptor
import okhttp3.Response

class DefaultInterceptor(
    private val getToken: GetToken
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        getToken()?.let { token ->
            request.addHeader("Authorization", token)
        }
        return chain.proceed(request.build())
    }
}