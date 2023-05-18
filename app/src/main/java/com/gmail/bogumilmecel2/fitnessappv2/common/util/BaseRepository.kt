package com.gmail.bogumilmecel2.fitnessappv2.common.util

import com.gmail.bogumilmecel2.fitnessappv2.common.data.singleton.TokenStatus
import retrofit2.HttpException

open class BaseRepository(
    private val realResourceProvider: RealResourceProvider
) {
    suspend inline fun <R> handleRequest(shouldHandleException: Boolean = true, block: () -> R): Resource<R> {
        return try {
            Resource.Success(block())
        } catch (e: Exception) {
            handleExceptionWithResource(
                exception = e,
                shouldHandleException = shouldHandleException
            )
        }
    }

    suspend fun <T> handleExceptionWithResource(
        exception: Exception,
        data: T? = null,
        shouldHandleException: Boolean
    ): Resource<T> {
        if (shouldHandleException) {
            exception.printStackTrace()
            if (exception is HttpException) {
                if (exception.code() == 401) {
                    TokenStatus.tokenNotPresent()
                }
            }
        }

        return Resource.Error(
            uiText = exception.message ?: realResourceProvider.getUnknownErrorString(),
            data = data
        )
    }
}