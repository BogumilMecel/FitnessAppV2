package com.gmail.bodziowaty6978.fitnessappv2.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.DarkGreyElevation2

@Composable
fun DefaultCardBackground(
    modifier: Modifier = Modifier,
    background: Color = DarkGreyElevation2,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .background(
                color = background,
                shape = defaultRoundedCornerShape()
            )
    ) {
        content()
    }
}