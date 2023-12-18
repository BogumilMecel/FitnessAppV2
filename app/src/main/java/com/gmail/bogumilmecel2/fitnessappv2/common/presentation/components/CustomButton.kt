package com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    buttonStyle: ButtonStyle = ButtonStyle.Button,
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = MaterialTheme.colors.onPrimary,
    iconLeft: ImageVector? = null,
    text: String,
    onClick: () -> Unit
) {
    when (buttonStyle) {
        is ButtonStyle.OutlinedButton -> {
            OutlinedButton(
                modifier = modifier,
                onClick = {
                    onClick()
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = backgroundColor,
                ),
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = buttonStyle.borderColor
                )
            ) {
                ButtonContent(
                    iconLeft = iconLeft,
                    text = text,
                    contentColor = contentColor
                )
            }
        }

        is ButtonStyle.Button -> {
            Button(
                modifier = modifier,
                onClick = {
                    onClick()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = backgroundColor
                ),
                shape = RoundedCornerShape(15.dp)
            ) {
                ButtonContent(
                    iconLeft = iconLeft,
                    text = text,
                    contentColor = contentColor
                )
            }
        }
    }
}

@Composable
private fun ButtonContent(
    iconLeft: ImageVector?,
    text: String,
    contentColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        iconLeft?.let {
            Icon(
                imageVector = iconLeft,
                contentDescription = text,
                tint = contentColor
            )
        }

        WidthSpacer(width = 8.dp)

        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.button,
            color = contentColor
        )
    }
}

sealed interface ButtonStyle {
    data class OutlinedButton(val borderColor: Color) : ButtonStyle
    object Button : ButtonStyle
}