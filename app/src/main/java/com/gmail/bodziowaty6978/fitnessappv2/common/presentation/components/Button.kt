package com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components

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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun Button(
    modifier: Modifier,
    buttonStyle: ButtonStyle = ButtonStyle.Button,
    backgroundColor: Color = MaterialTheme.colors.primary,
    textColor: Color = MaterialTheme.colors.onPrimary,
    iconLeft: ImageVector?,
    iconTint: Color = MaterialTheme.colors.onPrimary,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(15.dp),
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        border = if (buttonStyle is ButtonStyle.OutlinedButton) {
            BorderStroke(
                width = 1.dp,
                color = buttonStyle.borderColor
            )
        } else null
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
                    tint = iconTint
                )
            }

            WidthSpacer(width = 8.dp)

            Text(
                text = text.uppercase(),
                style = MaterialTheme.typography.button,
                color = textColor
            )
        }
    }
}

sealed interface ButtonStyle{
    data class OutlinedButton(val borderColor: Color) : ButtonStyle
    object Button: ButtonStyle
}