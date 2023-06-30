package com.gmail.bogumilmecel2.fitnessappv2.common.util

import kotlinx.coroutines.channels.Channel

interface BottomSheetContentProvider {
    val content: Channel<BottomSheetContent>

    suspend fun provideContent(content: BottomSheetContent)
}