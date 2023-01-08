package com.gmail.bodziowaty6978.fitnessappv2.common.util

open class BaseRepository(
    private val resourceProvider: ResourceProvider
) {

    fun <T> handleExceptionWithResource(exception: Exception, data: T? = null): Resource<T> {
        exception.printStackTrace()
        return Resource.Error(
            uiText = exception.message ?: resourceProvider.getUnknownErrorString(),
            data = data
        )
    }

    fun handleExceptionWithCustomResult(exception: Exception): CustomResult {
        exception.printStackTrace()
        return CustomResult.Error(
            message = exception.message ?: resourceProvider.getUnknownErrorString()
        )
    }
}