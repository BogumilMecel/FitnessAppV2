package com.gmail.bogumilmecel2.fitnessappv2.common.data.singleton

import kotlinx.coroutines.channels.Channel

object TokenStatus {
    val tokenStatus = Channel<Status>()

    suspend fun tokenNotPresent() {
        tokenStatus.send(Status.UNAVAILABLE)
    }

    enum class Status {
        UNAVAILABLE
    }
}