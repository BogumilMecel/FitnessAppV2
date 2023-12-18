package com.gmail.bogumilmecel2.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalTypography = staticCompositionLocalOf { FitnessAppTypography() }
val LocalColors = staticCompositionLocalOf { FitnessAppColors() }
val LocalFitnessAppTheme = staticCompositionLocalOf <FitnessAppThemeModel> { error("Theme provided") }

object FitnessAppTheme {
    val typography: FitnessAppTypography
        @ReadOnlyComposable
        @Composable get() = LocalTypography.current

    val colors: FitnessAppColors
        @ReadOnlyComposable
        @Composable get() = LocalColors.current
}

@Composable
fun FitnessAppTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalFitnessAppTheme provides FitnessAppThemeModel(
            colors = LocalColors.current,
            typography = LocalTypography.current
        )
    ) {
        MaterialTheme(
            colors = with(LocalColors.current) {
                darkColors(
                    primary = Primary,
                    primaryVariant = LocalColor.OrangeYellow1,
                    secondary = LocalColor.Quaternary,
                    secondaryVariant = LocalColor.Tertiary,
                    background = Background,
                    surface = Background,
                    onPrimary = Background,
                    onSecondary = Background,
                    onBackground = ContentPrimary,
                    onSurface = ContentPrimary,
                    error = Error
                )
            },
            typography = with(LocalTypography.current) {
                Typography(
                    defaultFontFamily = FitnessAppTypography.Roboto,
                    h1 = HeaderLarge,
                    h2 = HeaderLarge,
                    h3 = HeaderSmall,
                    h4 = HeaderSmall,
                    h5 = HeaderSmall,
                    h6 = HeaderSmall,
                    subtitle1 = ParagraphLarge,
                    subtitle2 = ParagraphMedium,
                    body1 = ParagraphLarge,
                    body2 = ParagraphMedium,
                    button = Button,
                    caption = ParagraphSmall,
                    overline = ParagraphSmall
                )
            }
        ) {
            content()
        }
    }
}

data class FitnessAppThemeModel(
    val colors: FitnessAppColors,
    val typography: FitnessAppTypography,
)

