package com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatToString(
):String{
    val format = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    return format.format(this)
}