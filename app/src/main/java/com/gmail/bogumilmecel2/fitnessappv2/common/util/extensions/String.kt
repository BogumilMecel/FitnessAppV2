package com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions

import kotlinx.datetime.LocalDateTime

fun String.toLocalDateTime() = LocalDateTime.parse(this)