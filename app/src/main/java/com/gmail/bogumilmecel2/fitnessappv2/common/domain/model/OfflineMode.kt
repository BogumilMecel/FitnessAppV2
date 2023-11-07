package com.gmail.bogumilmecel2.fitnessappv2.common.domain.model

enum class OfflineMode {
    Offline,
    Online;

    fun isOffline() = this == Offline
}