package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun LoaderColumn(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    isLoading: Boolean,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = modifier,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            content = content
        )

        AnimatedVisibility(
            visible = isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            TopLevelLoader()
        }
    }
}

@Composable
fun Loader(visible: Boolean) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        TopLevelLoader()
    }
}

@Composable
private fun TopLevelLoader() {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FitnessAppTheme.colors.BackgroundSecondary.copy(alpha = 0.5f))
            .clickable(
                onClick = { /* Block clicks on the background */ },
                indication = null, // Disable ripple effect
                interactionSource = interactionSource
            ),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(48.dp)
        )
    }
}