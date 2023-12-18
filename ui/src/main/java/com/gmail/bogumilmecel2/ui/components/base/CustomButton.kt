package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    buttonType: ButtonType = ButtonType.Button,
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = MaterialTheme.colors.onPrimary,
    iconLeft: IconParams? = null,
    text: String,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(15.dp)

    when (buttonType) {
        is ButtonType.OutlinedButton -> {
            OutlinedButton(
                modifier = modifier,
                onClick = {
                    onClick()
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = backgroundColor,
                ),
                shape = shape,
                border = BorderStroke(
                    width = 1.dp,
                    color = buttonType.borderColor
                )
            ) {
                ButtonContent(
                    iconLeft = iconLeft,
                    text = text,
                    contentColor = contentColor
                )
            }
        }

        is ButtonType.Button -> {
            Button(
                modifier = modifier,
                onClick = {
                    onClick()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = backgroundColor
                ),
                shape = shape
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
    iconLeft: IconParams?,
    text: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        iconLeft?.let {
            CustomIcon(
                iconParams = it
            )
        }

        WidthSpacer(width = 8.dp)

        CustomText(
            text = text,
            fitnessAppTextStyle = FitnessAppTextStyle.ButtonOnPrimary
        )
    }
}

sealed interface ButtonType {
    data class OutlinedButton(val borderColor: Color) : ButtonType
    object Button : ButtonType
}

sealed class ButtonStyle()