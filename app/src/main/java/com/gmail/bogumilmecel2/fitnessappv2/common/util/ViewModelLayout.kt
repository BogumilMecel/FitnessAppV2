package com.gmail.bogumilmecel2.fitnessappv2.common.util

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.ViewModel
import com.gmail.bogumilmecel2.ui.components.base.Loader
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
inline fun <STATE : Any, EVENT : Any, NAV_ARGUMENTS : Any> BaseViewModel<STATE, EVENT, NAV_ARGUMENTS>.ViewModelLayout(
    navigator: DestinationsNavigator,
    screenTestTag: String? = null,
    content: @Composable BaseViewModel<STATE, EVENT, NAV_ARGUMENTS>.() -> Unit = { }
) {
    Box(
        modifier = screenTestTag?.let { tag ->
            Modifier.testTag(tag)
        } ?: Modifier
    ) {
        LaunchedEffect(
            key1 = true,
            block = { configureOnStart() }
        )

        LaunchedEffect(
            key1 = true,
            block = { observeNetworkConnection() }
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

        content(this@ViewModelLayout)
        Loader(visible = loaderVisible)
    }
}

@Composable
inline fun <VM : ViewModel> ViewModel.ViewModelLayout2(
    navigator: DestinationsNavigator,
    screenTestTag: String? = null,
    content: @Composable VM.() -> Unit = { }
) {
    (this as? BaseViewModel2<*>)?.let {
        Box(
            modifier = screenTestTag?.let { tag ->
                Modifier.testTag(tag)
            } ?: Modifier
        ) {
            LaunchedEffect(
                key1 = true,
                block = { configureOnStart() }
            )

            LaunchedEffect(
                key1 = true,
                block = { observeNetworkConnection() }
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

            content(this@ViewModelLayout2 as VM)
            Loader(visible = loaderVisible)
        }
    }
}

@Composable
inline fun <STATE : Any, EVENT : Any, NAV_ARGUMENTS : Any, RESULT_BACK : Any> BaseResultViewModel<STATE, EVENT, NAV_ARGUMENTS, RESULT_BACK>.ViewModelLayout(
    navigator: DestinationsNavigator,
    resultBackNavigator: ResultBackNavigator<RESULT_BACK>,
    content: @Composable BaseViewModel<STATE, EVENT, NAV_ARGUMENTS>.() -> Unit = { }
) {
    LaunchedEffect(
        key1 = true,
        block = {
            resultBack.receiveAsFlow().collect {
                resultBackNavigator.navigateBack(result = it)
            }
        }
    )

    ViewModelLayout(
        navigator = navigator,
        content = content
    )
}

@Composable
inline fun <VM : ViewModel> ViewModel.ViewModelLayout2(
    navigator: DestinationsNavigator,
    resultBackNavigator: ResultBackNavigator<Any>,
    content: @Composable VM.() -> Unit = { }
) {
    (this as? BaseResultViewModel2<*, *>)?.let {
        LaunchedEffect(
            key1 = true,
            block = {
                resultBack.receiveAsFlow().collect {
                    resultBackNavigator.navigateBack(result = it)
                }
            }
        )
    }

    ViewModelLayout2(
        navigator = navigator,
        content = content
    )
}