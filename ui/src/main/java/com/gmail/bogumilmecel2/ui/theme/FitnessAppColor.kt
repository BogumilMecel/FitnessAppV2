package com.gmail.bogumilmecel2.ui.theme

import androidx.compose.ui.graphics.Color

data class FitnessAppColors(
    val Black: Color = Color.Black,
    val White: Color = Color.White,
    val Primary: Color = LocalColor.OrangeYellow1,
    val Secondary: Color = LocalColor.Secondary,
    val Background: Color = LocalColor.BackgroundDark,
    val BackgroundSecondary: Color = LocalColor.Grey,
    val BackgroundLight: Color = LocalColor.DarkGreyElevation9,
    val ContentPrimary: Color = LocalColor.Grey50,
    val ContentSecondary: Color = LocalColor.Grey250,
    val ContentTertiary: Color = LocalColor.Grey400,
    val ContentQuaternary: Color = LocalColor.Grey600,
    val Error: Color = LocalColor.RedError,
    val Transparent: Color = Color.Transparent
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
    val Secondary = Color(0xff54e1b6)
    val LightGreen2 = Color(0xff36ddab)
    val LightGreen3 = Color(0xff11d79b)
    val BlueViolet1 = Color(0xffaeb4fd)
    val BlueViolet2 = Color(0xff9fa5fe)
    val BlueViolet3 = Color(0xff8f98fd)

    val BackgroundDark = Color(0xFF121212)
    val DarkGreyElevation9 = Color(0xFF323232)
    val DarkGreyElevation6 = Color(0xFF2b2b2b)
    val DarkGreyElevation3 = Color(0xFF292929)
    val DarkGreyElevation2 = Color(0xFF272727)
    val DarkGreyElevation1 = Color(0xFF181818)
    val Grey = Color(0xFF1e1e1e)
    val LightGrey = Color(0xFF383838)
    val DarkGreyElevation = Color(0xFF242424)

    val Grey50 = Color(0xffeeeeee)
    val Grey250 = Color(0xFFbfbfbf)
    val Grey400 = Color(0xFF999999)
    val Grey600 = Color(0xFF595959)
    val Green = Color(0xFF81c784)
    val RedError = Color(0xFFe57373)
}