package com.gmail.bogumilmecel2.fitnessappv2.common.util

import com.gmail.bogumilmecel2.fitnessappv2.common.data.singleton.TokenStatus
import retrofit2.HttpException

open class BaseRepository {
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
        shouldHandleException: Boolean
    ): Resource.ComplexError<T> {
        if (shouldHandleException) {
            exception.printStackTrace()
            if (exception is HttpException) {
                if (exception.code() == 401) {
                    TokenStatus.tokenNotPresent()
                }
            }
        }

        return Resource.ComplexError(exception = exception)
    }
}