package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun TextWithTopIcon(
    modifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    text: String,
    icon: Icon,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomIcon(
            icon = icon,
            modifier = iconModifier
        )

        Text(
            text = text,
            style = FitnessAppTheme.typography.ImportantTextMedium,
            maxLines = 1,
        )
    }
}