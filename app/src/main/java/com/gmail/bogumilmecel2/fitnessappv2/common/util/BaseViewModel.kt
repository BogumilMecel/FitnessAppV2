package com.gmail.bogumilmecel2.fitnessappv2.common.util

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.connectivity.ConnectivityObserver
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.ConnectionState
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.navigation.NavigationAction
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.CheckConnectionStateUseCase
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.TAG
import com.ramcosta.composedestinations.spec.Direction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

abstract class BaseViewModel<STATE : Any, EVENT : Any, NAV_ARGUMENTS : Any>(
    state: STATE,
    val navArguments: NAV_ARGUMENTS
) : ViewModel() {

    protected val _state = MutableStateFlow(state)
    val __state: StateFlow<STATE> = _state

    val navigationDestination: Channel<NavigationAction> = Channel()

    var loaderVisible by mutableStateOf(false)

    @Inject
    lateinit var resourceProvider: ResourceProvider

    @Inject
    lateinit var cachedValuesProvider: CachedValuesProvider

    @Inject
    lateinit var connectivityObserver: ConnectivityObserver

//    @Inject
//    lateinit var checkConnectionStateUseCase: CheckConnectionStateUseCase

    protected fun showSnackbarError(message: String) {
        viewModelScope.launch {
            ErrorUtils.showSnackbarWithMessage(message = message)
        }
    }

    abstract fun onEvent(event: EVENT)

    open fun configureOnStart() {}

    fun observeNetworkConnection() {
        viewModelScope.launch {
            connectivityObserver.observe().collectLatest {
                val offlineMode = cachedValuesProvider.getOfflineMode()
                if ((it == ConnectionState.Available && offlineMode.isOffline())
                    || (it == ConnectionState.Unavailable && offlineMode.isOnline())
                ) {
//                    checkConnectionStateUseCase()
                }
            }
        }
    }

    protected inline fun <T> Resource<T>.handle(
        showSnackbar: Boolean = true,
        finally: () -> Unit = {},
        onError: (Exception) -> Unit = {},
        block: (T) -> Unit
    ) {
        when (this) {
            is Resource.ComplexError -> {
                Log.e(TAG, this.exception.toString())
                if (exception is HttpException) {
                    showSnackbarError(message = uiText)
                }

                onError(exception)
            }

            is Resource.Error -> {
                if (showSnackbar) {
                    showSnackbarError(message = uiText)
                }
            }

            is Resource.Success -> {
                block(this.data)
            }
        }
        finally()
    }

    protected fun navigateWithPopUp(destination: Direction, popUpTo: String = "") {
        navigateTo(
            destination = destination,
            navOptions = NavOptions.Builder().setPopUpTo(
                route = popUpTo,
                inclusive = true
            ).build()
        )
    }

    protected fun navigateTo(
        destination: Direction,
        navOptions: NavOptions = NavOptions.Builder().build()
    ) = viewModelScope.launch {
        navigationDestination.send(
            NavigationAction(
                direction = destination,
                navOptions = navOptions
            )
        )
    }

    protected fun navigateUp() {
        navigateTo(
            destination = object : Direction {
                override val route: String = "navigate_up"
            }
        )
    }
}

abstract class BaseViewModel2<EVENT : Any> : ViewModel() {

    val navigationDestination: Channel<NavigationAction> = Channel()

    var loaderVisible by mutableStateOf(false)

    @Inject
    lateinit var resourceProvider: ResourceProvider

    @Inject
    lateinit var cachedValuesProvider: CachedValuesProvider

    @Inject
    lateinit var connectivityObserver: ConnectivityObserver

//    @Inject
//    lateinit var checkConnectionStateUseCase: CheckConnectionStateUseCase

    protected fun showSnackbarError(message: String) {
        viewModelScope.launch {
            ErrorUtils.showSnackbarWithMessage(message = message)
        }
    }

    abstract fun onEvent(event: EVENT)

    open fun configureOnStart() {}

    fun observeNetworkConnection() {
        viewModelScope.launch {
            connectivityObserver.observe().collectLatest {
                val offlineMode = cachedValuesProvider.getOfflineMode()
                if ((it == ConnectionState.Available && offlineMode.isOffline())
                    || (it == ConnectionState.Unavailable && offlineMode.isOnline())
                ) {
//                    checkConnectionStateUseCase()
                }
            }
        }
    }

    protected inline fun <T> Resource<T>.handle(
        showSnackbar: Boolean = true,
        finally: () -> Unit = {},
        onError: (Exception) -> Unit = {},
        block: (T) -> Unit
    ) {
        when (this) {
            is Resource.ComplexError -> {
                if (exception is HttpException) {
                    showSnackbarError(message = uiText)
                }

                onError(exception)
            }

            is Resource.Error -> {
                if (showSnackbar) {
                    showSnackbarError(message = uiText)
                }
            }

            is Resource.Success -> {
                block(this.data)
            }
        }
        finally()
    }

    protected fun navigateWithPopUp(destination: Direction, popUpTo: String = "") {
        navigateTo(
            destination = destination,
            navOptions = NavOptions.Builder().setPopUpTo(
                route = popUpTo,
                inclusive = true
            ).build()
        )
    }

    protected fun navigateTo(
        destination: Direction,
        navOptions: NavOptions = NavOptions.Builder().build()
    ) = viewModelScope.launch {
        navigationDestination.send(
            NavigationAction(
                direction = destination,
                navOptions = navOptions
            )
        )
    }

    protected fun navigateUp() {
        navigateTo(
            destination = object : Direction {
                override val route: String = "navigate_up"
            }
        )
    }
}

abstract class BaseResultViewModel<STATE : Any, EVENT : Any, NAV_ARGUMENTS : Any, RESULT_BACK : Any>(
    state: STATE,
    navArguments: NAV_ARGUMENTS
) : BaseViewModel<STATE, EVENT, NAV_ARGUMENTS>(
    state = state,
    navArguments = navArguments
) {
    val resultBack: Channel<RESULT_BACK> = Channel()
}

abstract class BaseResultViewModel2<EVENT : Any, RESULT_BACK : Any> : BaseViewModel2<EVENT>() {
    val resultBack: Channel<RESULT_BACK> = Channel()
}