package com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions

fun <T> List<T>.removeAndReturnList(element: T): List<T> {
    return this.toMutableList().apply {
        remove(element)
        toList()
    }
}

fun <T> List<T>.addAndReturnList(element: T): List<T> {
    return this.toMutableList().apply {
        add(element)
        toList()
    }
}