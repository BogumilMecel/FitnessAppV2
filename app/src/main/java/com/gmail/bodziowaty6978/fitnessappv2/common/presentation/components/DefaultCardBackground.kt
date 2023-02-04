package com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DefaultCardBackground(
    content: @Composable () -> Unit,
    elevation: Int = 3,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = elevation.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
    ) {
        content()
    }
}