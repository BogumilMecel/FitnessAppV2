package com.gmail.bogumilmecel2.ui.components.base

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@Composable
fun CustomLazyColumn(
    modifier: Modifier = Modifier,
    onScrollToEnd: (() -> Unit)?,
    content: LazyListScope.() -> Unit
) {
    val state = rememberLazyListState()
    val isAtBottom = !state.canScrollForward

    LaunchedEffect(
        key1 = isAtBottom,
        block = {
            if (isAtBottom) onScrollToEnd?.invoke()
        }
    )

    LazyColumn(
        modifier = modifier,
        state = state,
        content = content
    )
}