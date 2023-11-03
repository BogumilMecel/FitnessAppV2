package com.gmail.bogumilmecel2.fitnessappv2.common.domain.connectivity

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.ConnectionState
import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe(): Flow<ConnectionState>
}