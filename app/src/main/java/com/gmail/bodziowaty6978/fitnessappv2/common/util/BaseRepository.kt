package com.gmail.bodziowaty6978.fitnessappv2.common.util

import com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton.TokenStatus
import retrofit2.HttpException

open class BaseRepository(
    private val resourceProvider: ResourceProvider
) {
    suspend inline fun <R> handleRequest(block: () -> R): Resource<R> {
        return try {
            Resource.Success(block())
        } catch (e: Exception) {
            handleExceptionWithResource(exception = e)
        }
    }

    suspend fun <T> handleExceptionWithResource(exception: Exception, data: T? = null): Resource<T> {
        exception.printStackTrace()
        if (exception is HttpException) {
            if (exception.code() == 401) {
                TokenStatus.tokenNotPresent()
            }
        }
        return Resource.Error(
            uiText = exception.message ?: resourceProvider.getUnknownErrorString(),
            data = data
        )
    }
}