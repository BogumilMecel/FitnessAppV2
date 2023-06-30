package com.gmail.bogumilmecel2.ui.components.complex

import androidx.compose.ui.Modifier
import com.chargemap.compose.numberpicker.ListItemPicker

@androidx.compose.runtime.Composable
fun FloatNumberPicker(
    modifier: Modifier,
    value: Float,
    minValue: Float,
    maxValue: Float,
    onValueChange: (Float) -> Unit
) {
    val realMinValue = (minValue / 0.1f).toInt()
    val realMaxValue = (maxValue / 0.1f).toInt()

    ListItemPicker(
        value = value,
        onValueChange = onValueChange,
        list = (realMinValue..realMaxValue).map {
            (it.toFloat() * 0.1f).round(1)
        },
        modifier = modifier
    )
}

private fun Float.round(decimals: Int): Float {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return (kotlin.math.round(this * multiplier) / multiplier).toFloat()
}