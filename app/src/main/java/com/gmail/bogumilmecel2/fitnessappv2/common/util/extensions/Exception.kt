package com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions

import retrofit2.HttpException

fun Exception.getHttpCode() = if (this is HttpException) code() else null