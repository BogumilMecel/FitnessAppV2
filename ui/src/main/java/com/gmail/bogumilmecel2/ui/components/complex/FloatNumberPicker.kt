package com.gmail.bogumilmecel2.ui.components.complex

import androidx.compose.ui.Modifier
import com.chargemap.compose.numberpicker.ListItemPicker

@androidx.compose.runtime.Composable
fun DoubleNumberPicker(
    modifier: Modifier,
    value: Double,
    minValue: Double,
    maxValue: Double,
    onValueChange: (Double) -> Unit
) {
    val realMinValue = (minValue / 0.1).toInt()
    val realMaxValue = (maxValue / 0.1).toInt()

    ListItemPicker(
        value = value,
        onValueChange = onValueChange,
        list = (realMinValue..realMaxValue).map {
            (it.toDouble() * 0.1).round(1)
        },
        modifier = modifier
    )
}

private fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return (kotlin.math.round(this * multiplier) / multiplier)
}