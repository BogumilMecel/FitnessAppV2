package com.gmail.bodziowaty6978.fitnessappv2.common.util

import kotlinx.coroutines.channels.Channel

object ErrorUtils {
    val errorState = Channel<String>()

    suspend fun showSnackbarWithMessage(message: String) {
        errorState.send(message)
    }
}