package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    buttonStyle: ButtonStyle = ButtonStyle.Primary,
    leftIcon: Icon? = null,
    text: String?,
    onClick: () -> Unit
) = with(buttonStyle) {
    val shape = RoundedCornerShape(15.dp)
    val backgroundColor = buttonStyle.getBackgroundColor()
    val contentColor = buttonStyle.getContentColor()

    when (buttonType) {
        ButtonType.OutlinedButton -> {
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
                    color = contentColor
                )
            ) {
                ButtonContent(
                    leftIcon = leftIcon,
                    text = text,
                    contentColor = contentColor
                )
            }
        }

        ButtonType.Button -> {
            Button(
                modifier = modifier,
                onClick = {
                    onClick()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = backgroundColor,
                ),
                shape = shape,
                elevation = when(buttonStyle) {
                    is ButtonStyle.Transparent -> ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        disabledElevation = 0.dp,
                        hoveredElevation = 0.dp,
                        focusedElevation = 0.dp
                    )
                    else -> ButtonDefaults.elevation()
                }
            ) {
                ButtonContent(
                    leftIcon = leftIcon,
                    text = text,
                    contentColor = contentColor
                )
            }
        }
    }
}

@Composable
private fun ButtonContent(
    leftIcon: Icon?,
    text: String?,
    contentColor: Color
) {
    Row(
        modifier = Modifier.width(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        leftIcon?.let { content ->
            CustomIcon(
                icon = content,
                iconColor = contentColor,
            )

            WidthSpacer(8.dp)
        }

        text?.let {
            Text(
                text = it.uppercase(),
                style = FitnessAppTheme.typography.Button,
                color = contentColor,
                maxLines = 1,
            )
        }
    }
}

enum class ButtonType {
    OutlinedButton, Button;
}

sealed class ButtonStyle(
    val buttonType: ButtonType = ButtonType.Button
) {
    data object Primary : ButtonStyle()
    data object OutlinedPrimary : ButtonStyle(buttonType = ButtonType.OutlinedButton)
    data object Secondary : ButtonStyle()
    data object Transparent : ButtonStyle()

    @Composable
    fun getBackgroundColor() = when (this) {
        is Primary -> FitnessAppTheme.colors.Primary
        is Secondary -> FitnessAppTheme.colors.Secondary
        is OutlinedPrimary, Transparent -> FitnessAppTheme.colors.Transparent
    }

    @Composable
    fun getContentColor() = when (this) {
        is Primary, Secondary -> FitnessAppTheme.colors.Black
        is OutlinedPrimary, Transparent -> FitnessAppTheme.colors.Primary
    }
}

data class ButtonParams(
    val text: String,
    val onClick: () -> Unit
)