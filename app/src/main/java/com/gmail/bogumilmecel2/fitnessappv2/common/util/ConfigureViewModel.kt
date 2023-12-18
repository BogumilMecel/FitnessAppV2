package com.gmail.bogumilmecel2.fitnessappv2.common.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun <STATE : Any, EVENT : Any, NAV_ARGUMENTS : Any> BaseViewModel<STATE, EVENT, NAV_ARGUMENTS>.ConfigureViewModel() {
    LaunchedEffect(
        key1 = true,
        block = { configureOnStart() }
    )
}