package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.theme.FitnessAppColor

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    buttonStyle: ButtonStyle = ButtonStyle.PrimaryButton,
    iconLeft: IconVector? = null,
    text: String,
    onClick: () -> Unit
) = with(buttonStyle) {
    val shape = RoundedCornerShape(15.dp)

    when (buttonType) {
        ButtonType.OutlinedButton -> {
            OutlinedButton(
                modifier = modifier,
                onClick = {
                    onClick()
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = backgroundColor(),
                ),
                shape = shape,
                border = BorderStroke(
                    width = 1.dp,
                    color = contentColor()
                )
            ) {
                ButtonContent(
                    iconLeft = iconLeft,
                    text = text,
                    contentColor = buttonStyle.contentColor
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
                    backgroundColor = backgroundColor()
                ),
                shape = shape
            ) {
                ButtonContent(
                    iconLeft = iconLeft,
                    text = text,
                    contentColor = buttonStyle.contentColor
                )
            }
        }
    }
}

@Composable
private fun ButtonContent(
    iconLeft: IconVector?,
    text: String,
    contentColor: FitnessAppColor
) {
    Row(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        iconLeft?.let {
            CustomIcon(
                iconStyle = it,
                iconColor = contentColor(),
            )

            WidthSpacer(width = 8.dp)
        }

        CustomText(
            text = text,
            fitnessAppTextStyle = FitnessAppTextStyle.Button(
                color = contentColor
            )
        )
    }
}

enum class ButtonType {
    OutlinedButton, Button;
}

sealed class ButtonStyle(
    open val backgroundColor: FitnessAppColor,
    open val contentColor: FitnessAppColor,
    val buttonType: ButtonType = ButtonType.Button
) {
    object PrimaryButton : ButtonStyle(
        backgroundColor = FitnessAppColor.Primary,
        contentColor = FitnessAppColor.Black
    )
    object OutlinedPrimaryButton: ButtonStyle(
        backgroundColor = FitnessAppColor.Transparent,
        contentColor = FitnessAppColor.Primary,
        buttonType = ButtonType.OutlinedButton
    )
    object ButtonSecondary: ButtonStyle(
        backgroundColor = FitnessAppColor.Secondary,
        contentColor = FitnessAppColor.Black
    )
}