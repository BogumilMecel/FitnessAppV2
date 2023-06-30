package com.gmail.bogumilmecel2.fitnessappv2.common.util

import androidx.compose.runtime.Composable

data class BottomSheetContent(
    val content: @Composable (() -> Unit)?,
    val onBottomSheetClosed: () -> Unit
)