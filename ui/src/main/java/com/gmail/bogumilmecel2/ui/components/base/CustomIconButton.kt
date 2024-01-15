package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun CustomIconButton(
    modifier: Modifier = Modifier,
    icon: Icon,
    enabled: Boolean = true,
    tint: Color = FitnessAppTheme.colors.Primary,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
    ) {
        CustomIcon(
            icon = icon,
            tint = tint
        )
    }
}

@Composable
fun CustomIconButtonSmall(
    modifier: Modifier = Modifier,
    icon: Icon,
    tint: Color = FitnessAppTheme.colors.Primary,
    onClick: () -> Unit
) {
    CustomIcon(
        icon = icon,
        tint = tint,
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .padding(4.dp)
    )
}

data class IconButtonParams(
    val iconVector: IconVector,
    val onClick: () -> Unit,
    val enabled: Boolean = true
)