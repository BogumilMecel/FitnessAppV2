package com.gmail.bogumilmecel2.fitnessappv2.common.util

import kotlinx.coroutines.channels.Channel

class BottomBarStatusProvider {
    val bottomBarEvent = Channel<BottomBarEvent>()
    var onBottomBarClicked: (() -> Unit)? = null
}

sealed interface BottomBarEvent {
    object Hide: BottomBarEvent
    object Show: BottomBarEvent
}