package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.gmail.bogumilmecel2.ui.theme.FitnessAppColors
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun CustomText(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    fitnessAppTextStyle: FitnessAppTextStyle
) {
    val textStyle = fitnessAppTextStyle.getTextStyle()

    Text(
        modifier = modifier,
        text = if (fitnessAppTextStyle.upperCase) text.uppercase() else if (fitnessAppTextStyle.capitalize) text.replaceFirstChar { it.uppercaseChar() } else text,
        textAlign = textAlign,
        style = textStyle,
        color = fitnessAppTextStyle.color()
    )
}

@Composable
fun FitnessAppTextStyle.getTextStyle(): TextStyle {
    return when (this) {
        is FitnessAppTextStyle.ButtonOnPrimary, FitnessAppTextStyle.ButtonOnSurface -> FitnessAppTheme.typography.Button

        is FitnessAppTextStyle.CardTitle -> FitnessAppTheme.typography.HeaderSmall

        is FitnessAppTextStyle.HeaderLarge -> FitnessAppTheme.typography.HeaderLarge

        is
        FitnessAppTextStyle.ParagraphLarge,
        FitnessAppTextStyle.InputDescription -> FitnessAppTheme.typography.ParagraphLarge

        is
        FitnessAppTextStyle.ParagraphMedium,
        FitnessAppTextStyle.ParagraphSecondary,
        FitnessAppTextStyle.TextFieldPlaceholder -> FitnessAppTheme.typography.ParagraphMedium

        is FitnessAppTextStyle.Error -> FitnessAppTheme.typography.ParagraphSmall
    }
}

sealed class FitnessAppTextStyle(
    val color: FitnessAppColors = FitnessAppColors.ContentWhite,
    val upperCase: Boolean = false,
    val capitalize: Boolean = true
) {
    object ButtonOnPrimary : FitnessAppTextStyle(
        upperCase = true,
        color = FitnessAppColors.Black
    )

    object ButtonOnSurface : FitnessAppTextStyle(
        upperCase = true,
        color = FitnessAppColors.Primary
    )

    object CardTitle : FitnessAppTextStyle(color = FitnessAppColors.ContentWhite)
    object ParagraphSecondary : FitnessAppTextStyle(color = FitnessAppColors.ContentSecondary)
    object ParagraphMedium : FitnessAppTextStyle()
    object ParagraphLarge : FitnessAppTextStyle()
    object HeaderLarge : FitnessAppTextStyle()
    object TextFieldPlaceholder : FitnessAppTextStyle(color = FitnessAppColors.TextGreyPlaceholder)
    object InputDescription : FitnessAppTextStyle()
    object Error : FitnessAppTextStyle(color = FitnessAppColors.Error)
}