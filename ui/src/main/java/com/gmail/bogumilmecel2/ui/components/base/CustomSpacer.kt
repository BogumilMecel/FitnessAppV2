package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun HeightSpacer(height: Dp = 16.dp) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun ColumnScope.FullHeightSpacer() {
    Spacer(modifier = Modifier.weight(1f))
}

@Composable
fun WidthSpacer(width: Dp = 16.dp) {
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun WidthDivider() {
    Divider(
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth(),
        color = FitnessAppTheme.colors.BackgroundQuaternary
    )
}

@Composable
fun VerticalDivider(
    height: Dp? = null,
    thickness: Dp = 1.dp,
    color: Color = FitnessAppTheme.colors.BackgroundQuaternary
) {
    Box(
        modifier = Modifier
            .then(
                other = if (height == null) {
                    Modifier.fillMaxHeight()
                } else {
                    Modifier.height(height)
                }
            )
            .width(thickness)
            .background(color = color)
    )
}