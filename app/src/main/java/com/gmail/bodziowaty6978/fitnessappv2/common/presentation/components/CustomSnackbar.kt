package com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components

import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.DarkGreyElevation6

@Composable
fun CustomSnackbar(
    snackbarHostState: SnackbarHostState
) {
    SnackbarHost(hostState = snackbarHostState, snackbar = { snackbarData ->
        Snackbar(
            snackbarData = snackbarData,
            backgroundColor = DarkGreyElevation6,
            contentColor = Color.White,
            elevation = 4.dp
        )
    })
}