package com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions

val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }