package com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatToString(
):String{
    val format = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    return format.format(this)
}