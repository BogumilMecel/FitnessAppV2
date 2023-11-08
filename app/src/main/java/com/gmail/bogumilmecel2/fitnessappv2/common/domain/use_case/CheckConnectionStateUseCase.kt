package com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.connectivity.ConnectivityObserver
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.OfflineMode
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider

class CheckConnectionStateUseCase(
    private val connectivityObserver: ConnectivityObserver,
    private val cachedValuesProvider: CachedValuesProvider
) {
    suspend operator fun invoke() {
        cachedValuesProvider.setOfflineMode(
            offlineMode = if (connectivityObserver.isOnline()) {
                OfflineMode.Online
            } else {
                OfflineMode.Offline
            }
        )
    }
}