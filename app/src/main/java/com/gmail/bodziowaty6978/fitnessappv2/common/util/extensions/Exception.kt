package com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions

import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider

fun <T> Exception.handleResource(resourceProvider: ResourceProvider, data: T? = null): Resource<T> {
    this.printStackTrace()
    return Resource.Error(
        uiText = this.message ?: resourceProvider.getUnknownErrorString()
    )
}

fun Exception.handleCustomResult(resourceProvider: ResourceProvider): CustomResult {
    this.printStackTrace()
    return CustomResult.Error(
        message = this.message ?: resourceProvider.getUnknownErrorString()
    )
}