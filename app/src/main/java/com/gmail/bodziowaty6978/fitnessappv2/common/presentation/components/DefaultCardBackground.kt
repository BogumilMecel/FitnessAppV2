package com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.DarkGreyElevation6

@Composable
fun DefaultCardBackground(
    modifier: Modifier = Modifier,
    background: Color = DarkGreyElevation6,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .background(
                color = background,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        content()
    }
}