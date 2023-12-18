package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun ClickableText(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Text(
        text = text,
        style = FitnessAppTheme.typography.ParagraphMedium,
        textAlign = TextAlign.Center,
        modifier = modifier
            .clip(FitnessAppTheme.shapes.Medium)
            .clickable { onClick() }
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
    )
}