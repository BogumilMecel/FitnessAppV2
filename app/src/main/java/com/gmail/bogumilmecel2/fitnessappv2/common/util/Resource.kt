package com.gmail.bogumilmecel2.fitnessappv2.common.util

import retrofit2.HttpException

sealed class Resource<T>(open val data: T? = null) {
    data class Success<T>(override val data: T) : Resource<T>()
    open class Error<T>(val uiText: String = "unknown error") : Resource<T>()
    data class ComplexError<T>(val exception: Exception) :
        Error<T>(uiText = exception.message ?: "unknown error") {

        fun getHttpCode() = if (exception is HttpException) exception.code() else null

    }
}