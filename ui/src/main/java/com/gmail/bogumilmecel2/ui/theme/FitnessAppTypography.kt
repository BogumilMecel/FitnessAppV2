package com.gmail.bogumilmecel2.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.gmail.bogumilmecel2.ui.R
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTypography.Companion.Roboto

data class FitnessAppTypography(
    val Button: TextStyle = text(fontSize = 14.sp, fontWeight = FontWeight.Medium),
    val ParagraphLarge: TextStyle = paragraph(fontSize = 16.sp),
    val ParagraphMedium: TextStyle = paragraph(fontSize = 14.sp),
    val ParagraphSmall: TextStyle = paragraph(fontSize = 12.sp),
    val ImportantTextMedium: TextStyle = text(fontSize = 14.sp, fontWeight = FontWeight.Medium),
    val HeaderLarge: TextStyle = header(fontSize = 20.sp),
    val HeaderSmall: TextStyle = header(fontSize = 16.sp)
) {
    companion object {
        val Roboto = FontFamily(
            Font(R.font.roboto_regular, FontWeight.Normal),
            Font(R.font.roboto_medium, FontWeight.Medium),
            Font(R.font.roboto_bold, FontWeight.Bold)
        )
    }
}

private fun header(fontSize: TextUnit) = text(fontSize = fontSize, fontWeight = FontWeight.Bold)

private fun paragraph(fontSize: TextUnit) = text(fontSize = fontSize, fontWeight = FontWeight.Normal)

private fun text(fontSize: TextUnit, fontWeight: FontWeight) = TextStyle(
    fontSize = fontSize,
    fontFamily = Roboto,
    fontWeight = fontWeight,
    lineHeight = 1.5.em,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    )
)