package com.gmail.bodziowaty6978.fitnessappv2.components.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import com.gmail.bodziowaty6978.fitnessappv2.components.FitnessAppColor

val LocalTypography = staticCompositionLocalOf { FitnessAppTypography() }
val LocalColor = staticCompositionLocalOf { FitnessAppColor() }

object FitnessAppTheme {
    val typography: FitnessAppTypography
        @Composable get() = LocalTypography.current

    val colors: FitnessAppColor
        @Composable get() = LocalColor.current
}

