package com.gmail.bogumilmecel2.fitnessappv2.components

import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.gmail.bogumilmecel2.ui.theme.LocalColor.BackgroundTertiary

@Composable
fun DefaultCardBackground(
    modifier: Modifier = Modifier,
    background: Color = BackgroundTertiary,
    content: @Composable () -> Unit,
) {
    Card(
        backgroundColor = background,
        shape = defaultRoundedCornerShape(),
        modifier = modifier
    ) {
        content()
    }
}