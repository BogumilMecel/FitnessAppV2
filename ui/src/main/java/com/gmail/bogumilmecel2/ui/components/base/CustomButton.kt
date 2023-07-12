package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
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
    leftContent: LeftContent? = null,
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
                    leftContent = leftContent,
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
                    backgroundColor = backgroundColor
                ),
                shape = shape
            ) {
                ButtonContent(
                    leftContent = leftContent,
                    text = text,
                    contentColor = contentColor
                )
            }
        }
    }
}

@Composable
private fun ButtonContent(
    leftContent: LeftContent?,
    text: String?,
    contentColor: Color
) {
    Row(
        modifier = Modifier
            .height(28.dp)
            .width(IntrinsicSize.Max),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxHeight()
        ) {
            leftContent?.let { content ->
                when(content) {
                    is LeftContent.Icon -> CustomIcon(
                        iconVector = content.iconVector,
                        iconColor = contentColor,
                    )

                    is LeftContent.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = contentColor,
                            strokeWidth = 3.dp
                        )
                    }
                }
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
}

sealed interface LeftContent {
    data class Icon(val iconVector: IconVector): LeftContent
    object Loading: LeftContent
}

enum class ButtonType {
    OutlinedButton, Button;
}

sealed class ButtonStyle(
    val buttonType: ButtonType = ButtonType.Button
) {
    object Primary : ButtonStyle()
    object OutlinedPrimary: ButtonStyle(buttonType = ButtonType.OutlinedButton)
    object Secondary: ButtonStyle()

    @Composable
    fun getBackgroundColor() = when(this) {
        is Primary -> FitnessAppTheme.colors.Primary
        is Secondary -> FitnessAppTheme.colors.Secondary
        is OutlinedPrimary -> FitnessAppTheme.colors.Transparent
    }

    @Composable
    fun getContentColor() = when(this) {
        is Primary, Secondary -> FitnessAppTheme.colors.Black
        is OutlinedPrimary -> FitnessAppTheme.colors.Primary
    }
}

data class ButtonParams(
    val text: String,
    val onClick: () -> Unit
)