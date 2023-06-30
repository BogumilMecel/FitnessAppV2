package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun CustomIconButton(
    params: IconButtonParams,
    iconColor: Color = FitnessAppTheme.colors.Primary
) {
    IconButton(
        onClick = {
            params.onClick()
        },
        enabled = params.enabled
    ) {
        CustomIcon(
            iconStyle = params.iconVector,
            iconColor = iconColor
        )
    }
}

data class IconButtonParams(
    val iconVector: IconVector,
    val onClick: () -> Unit,
    val enabled: Boolean = true
)