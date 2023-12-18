package com.gmail.bogumilmecel2.fitnessappv2.common.util

sealed class Resource<T>(open val data: T? = null, open val uiText: String? = null) {
    data class Success<T>(override val data: T, override val uiText: String? = null) : Resource<T>(data)
    data class Error<T>(override val uiText: String, override val data: T? = null) : Resource<T>(data, uiText)
}