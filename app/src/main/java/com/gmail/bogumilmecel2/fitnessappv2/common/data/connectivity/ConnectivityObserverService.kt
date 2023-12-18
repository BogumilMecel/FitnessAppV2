package com.gmail.bogumilmecel2.fitnessappv2.common.data.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.connectivity.ConnectivityObserver
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.ConnectionState
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.data.api.LoadingApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class ConnectivityObserverService(
    private val loadingApi: LoadingApi,
    context: Context,
) : ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager?

    override fun observe(): Flow<ConnectionState> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(ConnectionState.Available)
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                trySend(ConnectionState.Unavailable)
            }

            override fun onLost(network: Network) {
                trySend(ConnectionState.Unavailable)
            }

            override fun onUnavailable() {
                trySend(ConnectionState.Unavailable)
            }
        }

        connectivityManager?.registerDefaultNetworkCallback(callback)

        awaitClose {
            connectivityManager?.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged()

    override suspend fun isOnline() = hasActiveNetwork() && hasAccessToBackend()

    private fun hasActiveNetwork(): Boolean {
        connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
            if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            ) return true
        }

        return false
    }

    private suspend fun hasAccessToBackend(): Boolean {
        return try {
            loadingApi.isReachable()
            true
        } catch (e: Exception) {
            false
        }
    }
}