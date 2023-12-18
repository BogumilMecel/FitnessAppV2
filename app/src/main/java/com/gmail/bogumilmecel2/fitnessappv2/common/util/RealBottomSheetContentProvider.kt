package com.gmail.bogumilmecel2.fitnessappv2.common.util

import kotlinx.coroutines.channels.Channel

class RealBottomSheetContentProvider : BottomSheetContentProvider {

    private val _content: Channel<BottomSheetContent> = Channel()
    override val content: Channel<BottomSheetContent> = _content

    override suspend fun provideContent(content: BottomSheetContent) {
        _content.send(content)
    }
}