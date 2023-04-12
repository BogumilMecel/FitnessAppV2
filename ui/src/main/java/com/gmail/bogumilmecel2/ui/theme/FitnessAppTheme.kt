package com.gmail.bogumilmecel2.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalTypography = staticCompositionLocalOf { FitnessAppTypography() }
val LocalColor = staticCompositionLocalOf { FitnessAppColor() }

object FitnessAppTheme {
    val typography: FitnessAppTypography
        @Composable get() = LocalTypography.current

    val colors: FitnessAppColor
        @Composable get() = LocalColor.current
}

