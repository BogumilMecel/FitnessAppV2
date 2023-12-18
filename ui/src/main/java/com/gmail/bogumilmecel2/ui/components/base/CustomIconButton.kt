package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable

@Composable
fun CustomIconButton(
    iconParams: IconParams
) {
    IconButton(
        onClick = {
            iconParams.onClick()
        }
    ) {
        CustomIcon(iconStyle = iconParams.iconStyle)
    }
}

data class IconParams(
    val iconStyle: CustomIconStyle,
    val onClick: () -> Unit
)