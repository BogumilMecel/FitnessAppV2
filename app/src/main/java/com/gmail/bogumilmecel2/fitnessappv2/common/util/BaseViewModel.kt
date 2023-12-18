package com.gmail.bogumilmecel2.fitnessappv2.common.util

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.navigation.NavigationAction
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.ramcosta.composedestinations.spec.Direction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel<STATE : Any, EVENT : Any, NAV_ARGUMENTS : Any>(
    state: STATE,
    val navArguments: NAV_ARGUMENTS
) : ViewModel() {

    protected val _state = MutableStateFlow(state)
    val state: StateFlow<STATE> = _state

    val navigationDestination: Channel<NavigationAction> = Channel()

    var loaderVisible by mutableStateOf(false)

    @Inject
    lateinit var cachedValuesProvider: CachedValuesProvider

    @Inject
    lateinit var resourceProvider: ResourceProvider

    protected fun showSnackbarError(message: String) {
        viewModelScope.launch {
            ErrorUtils.showSnackbarWithMessage(message = message)
        }
    }

    abstract fun onEvent(event: EVENT)

    open fun configureOnStart() {}

    protected inline fun <T> Resource<T>.handle(
        showSnackbar: Boolean = true,
        finally: () -> Unit = {},
        onError: (String) -> Unit = {},
        block: (T) -> Unit
    ) {
        if (this is Resource.Error) {
            if (showSnackbar) {
                showSnackbarError(message = this.uiText)
            }
            onError(this.uiText)
        } else if (this is Resource.Success) {
            block(this@handle.data)
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