package com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate

@Composable
fun DropdownArrow(
    modifier: Modifier = Modifier,
    isArrowPointedDownwards: Boolean,
    onArrowClicked: () -> Unit
) {
    val dropdownArrowState by animateFloatAsState(
        targetValue = if (isArrowPointedDownwards) 180f else 0f
    )

    IconButton(
        onClick = {
            onArrowClicked()
        },
        modifier = modifier
            .rotate(
                degrees = dropdownArrowState
            )
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null
        )
    }
}