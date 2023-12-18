package com.gmail.bogumilmecel2.fitnessappv2.common.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.gmail.bogumilmecel2.ui.components.base.Loader
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
inline fun <STATE : Any, EVENT : Any, NAV_ARGUMENTS : Any> BaseViewModel<STATE, EVENT, NAV_ARGUMENTS>.ViewModelLayout(
    navigator: DestinationsNavigator,
    content: @Composable (BaseViewModel<STATE, EVENT, NAV_ARGUMENTS>) -> Unit = {}
) {
    LaunchedEffect(
        key1 = true,
        block = { configureOnStart() }
    )

    LaunchedEffect(
        key1 = true,
        block = {
            navigationDestination.receiveAsFlow().collect {
                if (it.direction.route == "navigate_up") {
                    navigator.navigateUp()
                } else {
                    navigator.navigate(
                        direction = it.direction,
                        navOptions = it.navOptions
                    )
                }
            }
        }
    )

    content(this)
    Loader(visible = loaderVisible)
}