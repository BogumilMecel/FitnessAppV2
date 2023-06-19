package com.gmail.bogumilmecel2.fitnessappv2.common.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.navigation.NavigationAction
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.ramcosta.composedestinations.spec.Direction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

abstract class BaseViewModel<STATE: Any, EVENT: Any>(state: STATE) : ViewModel() {

    protected val _state = MutableStateFlow(state)
    val state: StateFlow<STATE> = _state

    @Inject
    lateinit var navigator: Navigator

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

    protected fun navigateWithPopUp(destination: Direction) {
        navigateTo(
            destination = destination,
            navOptions = NavOptions.Builder().setPopUpTo(
                route = "pop_up",
                inclusive = true
            ).build()
        )
    }

    protected fun navigateTo(destination: Direction, navOptions: NavOptions = NavOptions.Builder().build()) =
        viewModelScope.launch {
            navigator.navigate(
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

    protected fun getCurrentTimestamp() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        .toInstant(UtcOffset.ZERO).toEpochMilliseconds()
}