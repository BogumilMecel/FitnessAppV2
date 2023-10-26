package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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