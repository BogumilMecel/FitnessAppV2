package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun CustomIconButton(
    iconParams: IconParams,
    iconColor: Color = FitnessAppTheme.colors.Primary
) {
    IconButton(
        onClick = {
            iconParams.onClick()
        },
    ) {
        CustomIcon(
            iconStyle = iconParams.iconStyle,
            iconColor = iconColor
        )
    }
}

data class IconParams(
    val iconStyle: CustomIconStyle,
    val onClick: () -> Unit
)