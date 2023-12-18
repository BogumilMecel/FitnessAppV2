package com.gmail.bodziowaty6978.fitnessappv2.common.util

open class BaseRepository(
    private val resourceProvider: ResourceProvider
) {
    inline fun <R> handleRequest(block: () -> R): Resource<R> {
        return try {
            Resource.Success(block())
        } catch (e: Exception) {
            handleExceptionWithResource(exception = e)
        }
    }

    fun <T> handleExceptionWithResource(exception: Exception, data: T? = null): Resource<T> {
        exception.printStackTrace()
        return Resource.Error(
            uiText = exception.message ?: resourceProvider.getUnknownErrorString(),
            data = data
        )
    }
}