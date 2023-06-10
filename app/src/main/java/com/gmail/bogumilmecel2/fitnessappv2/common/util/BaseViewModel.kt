package com.gmail.bogumilmecel2.fitnessappv2.common.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.navigation.NavigationAction
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.navigation.Navigator
import com.ramcosta.composedestinations.spec.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var cachedValuesProvider: CachedValuesProvider

    @Inject
    lateinit var resourceProvider: ResourceProvider

    fun showSnackbarError(message: String) {
        viewModelScope.launch {
            ErrorUtils.showSnackbarWithMessage(message = message)
        }
    }

    inline fun <T> Resource<T>.handle(
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
            block(this.data)
        }
        finally()
    }

    fun navigateWithPopUp(destination: Direction) {
        navigateTo(
            destination = destination,
            navOptions = NavOptions.Builder().setPopUpTo(
                route = "pop_up",
                inclusive = true
            ).build()
        )
    }

    fun navigateTo(destination: Direction, navOptions: NavOptions = NavOptions.Builder().build()) =
        viewModelScope.launch {
            navigator.navigate(
                NavigationAction(
                    direction = destination,
                    navOptions = navOptions
                )
            )
        }

    fun navigateUp() {
        navigateTo(
            destination = object : Direction {
                override val route: String = "navigate_up"
            }
        )
    }
}