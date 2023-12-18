package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.gmail.bogumilmecel2.ui.theme.FitnessAppColor
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
        FitnessAppTextStyle.ParagraphSecondaryLarge,
        FitnessAppTextStyle.InputDescription -> FitnessAppTheme.typography.ParagraphLarge

        is
        FitnessAppTextStyle.ParagraphMedium,
        FitnessAppTextStyle.ParagraphSecondaryMedium,
        FitnessAppTextStyle.TextFieldPlaceholder -> FitnessAppTheme.typography.ParagraphMedium

        is FitnessAppTextStyle.Error -> FitnessAppTheme.typography.ParagraphSmall
        is FitnessAppTextStyle.ImportantTextMedium -> FitnessAppTheme.typography.ImportantTextMedium
    }
}

sealed class FitnessAppTextStyle(
    val color: FitnessAppColor = FitnessAppColor.ContentWhite,
    val upperCase: Boolean = false,
    val capitalize: Boolean = true
) {
    object ButtonOnPrimary : FitnessAppTextStyle(
        upperCase = true,
        color = FitnessAppColor.Black
    )

    object ButtonOnSurface : FitnessAppTextStyle(
        upperCase = true,
        color = FitnessAppColor.Primary
    )

    object CardTitle : FitnessAppTextStyle()
    object ParagraphSecondaryMedium : FitnessAppTextStyle(color = FitnessAppColor.ContentSecondary)
    object ParagraphSecondaryLarge : FitnessAppTextStyle(color = FitnessAppColor.ContentSecondary)
    object ImportantTextMedium : FitnessAppTextStyle()
    object ParagraphMedium : FitnessAppTextStyle()
    object ParagraphLarge : FitnessAppTextStyle()
    object HeaderLarge : FitnessAppTextStyle()
    object TextFieldPlaceholder : FitnessAppTextStyle(color = FitnessAppColor.TextGreyPlaceholder)
    object InputDescription : FitnessAppTextStyle()
    object Error : FitnessAppTextStyle(color = FitnessAppColor.Error)
}