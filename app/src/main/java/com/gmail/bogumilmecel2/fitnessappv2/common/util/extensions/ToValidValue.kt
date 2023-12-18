package com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions

fun String.toValidInt(): Int? = this.replace(",", "").replace(".", "").toIntOrNull()
fun String.toValidDouble(): Double? = this.replace(",", ".").toDoubleOrNull()