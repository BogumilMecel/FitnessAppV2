package com.gmail.bogumilmecel2.fitnessappv2.common.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun <STATE : Any, EVENT : Any, NAV_ARGUMENTS : Any> BaseViewModel<STATE, EVENT, NAV_ARGUMENTS>. ConfigureViewModel(
    navigator: DestinationsNavigator
) {
    LaunchedEffect(
        key1 = true,
        block = { configureOnStart() }
    )

    LaunchedEffect(
        key1 = true,
        block = {
            navigationDestination.receiveAsFlow().collect {
                navigator.navigate(
                    direction = it.direction,
                    navOptions = it.navOptions
                )
            }
        }
    )
}