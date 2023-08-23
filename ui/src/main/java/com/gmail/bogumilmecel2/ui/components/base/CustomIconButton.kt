package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    params: IconButtonParams,
    iconColor: Color = FitnessAppTheme.colors.Primary
) {
    IconButton(
        modifier = modifier,
        enabled = params.enabled,
        onClick = { params.onClick() },
    ) {
        CustomIcon(
            icon = params.iconVector,
            iconColor = iconColor
        )
    }
}

data class IconButtonParams(
    val iconVector: IconVector,
    val onClick: () -> Unit,
    val enabled: Boolean = true
)