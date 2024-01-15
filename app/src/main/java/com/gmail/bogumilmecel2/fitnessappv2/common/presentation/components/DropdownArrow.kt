package com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import com.gmail.bogumilmecel2.ui.components.base.CustomIconButton
import com.gmail.bogumilmecel2.ui.components.base.CustomIconButtonSmall
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun DropdownArrow(
    modifier: Modifier = Modifier,
    isArrowPointedDownwards: Boolean,
    onArrowClicked: () -> Unit
) {
    val dropdownArrowState by animateFloatAsState(
        targetValue = if (isArrowPointedDownwards) 180f else 0f,
        label = "arrow down rotation animation"
    )

    CustomIconButton(
        modifier = modifier.rotate(degrees = dropdownArrowState),
        icon = IconVector.ArrowDown,
        onClick = onArrowClicked
    )
}

@Composable
fun DropdownArrowSmall(
    modifier: Modifier = Modifier,
    isArrowPointedDownwards: Boolean,
    onArrowClicked: () -> Unit,
) {
    val dropdownArrowState by animateFloatAsState(
        targetValue = if (isArrowPointedDownwards) 180f else 0f,
        label = "arrow down rotation animation"
    )

    CustomIconButtonSmall(
        modifier = modifier.rotate(degrees = dropdownArrowState),
        icon = IconVector.ArrowDown,
        onClick = onArrowClicked,
        tint = FitnessAppTheme.colors.ContentPrimary
    )
}