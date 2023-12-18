package com.gmail.bogumilmecel2.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class FitnessAppColors(
    val Black: Color = Color.Black,
    val White: Color = Color.White,
    val Primary: Color = LocalColor.OrangeYellow1,
    val Background: Color = LocalColor.DarkGrey,
    val ContentPrimary: Color = LocalColor.ContentPrimary,
    val ContentSecondary: Color = LocalColor.ContentSecondary,
    val Error: Color = LocalColor.RedError
)

object LocalColor {
    val DeepBlueElevation = Color(0xff212f5f)
    val Blue = Color(0xFF655fb1)
    val ButtonBlue = Color(0xff505cf3)
    val DarkerButtonBlue = Color(0xff566894)
    val LightRed = Color(0xfffc879a)
    val AquaBlue = Color(0xff9aa5c4)
    val OrangeYellow3 = Color(0xfff0bd28)
    val OrangeYellow2 = Color(0xfff1c746)
    val OrangeYellow1 = Color(0xfff4cf65)
    val Beige1 = Color(0xfffdbda1)
    val Beige2 = Color(0xfffcaf90)
    val Beige3 = Color(0xfff9a27b)
    val LightGreen1 = Color(0xff54e1b6)
    val LightGreen2 = Color(0xff36ddab)
    val LightGreen3 = Color(0xff11d79b)
    val BlueViolet1 = Color(0xffaeb4fd)
    val BlueViolet2 = Color(0xff9fa5fe)
    val BlueViolet3 = Color(0xff8f98fd)

    val DarkGrey = Color(0xFF121212)
    val DarkGreyElevation9 = Color(0xFF323232)
    val DarkGreyElevation6 = Color(0xFF2b2b2b)
    val DarkGreyElevation3 = Color(0xFF292929)
    val DarkGreyElevation2 = Color(0xFF272727)
    val DarkGreyElevation1 = Color(0xFF181818)
    val Grey = Color(0xFF1e1e1e)
    val LightGrey = Color(0xFF383838)
    val DarkGreyElevation = Color(0xFF242424)

    val ContentPrimary = Color(0xffeeeeee)
    val ContentSecondary = Color(0xFFbfbfbf)
    val TextGreyPlaceholder = Color(0xFF999999)
    val Green = Color(0xFF81c784)
    val RedError = Color(0xFFe57373)
}

enum class FitnessAppColor {
    Black,
    White,
    Primary,
    ContentWhite,
    ContentSecondary,
    TextGreyPlaceholder,
    Calories,
    Carbohydrates,
    Protein,
    Fat,
    Error;

    @Composable
    operator fun invoke() = when (this) {
        Black -> Color.Black
        White -> Color.White
        Error -> LocalColor.RedError
        ContentWhite -> LocalColor.ContentPrimary
        ContentSecondary -> LocalColor.ContentSecondary
        else -> Color.White
    }
}