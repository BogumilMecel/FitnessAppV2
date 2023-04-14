package com.gmail.bogumilmecel2.fitnessappv2.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gmail.bogumilmecel2.ui.theme.LocalColor.DarkGreyElevation2

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