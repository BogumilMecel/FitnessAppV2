package com.gmail.bogumilmecel2.ui.utils.extensions

fun CharSequence.isDigitsOnly() = all(Char::isDigit) && isNotEmpty()