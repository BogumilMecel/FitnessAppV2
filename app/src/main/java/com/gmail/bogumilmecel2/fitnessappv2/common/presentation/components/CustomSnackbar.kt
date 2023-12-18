package com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components

import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.common.util.TestTags
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun CustomSnackbar(
    snackbarHostState: SnackbarHostState
) {
    SnackbarHost(hostState = snackbarHostState, snackbar = { snackbarData ->
        Snackbar(
            modifier = Modifier.testTag(TestTags.SNACKBAR),
            snackbarData = snackbarData,
            backgroundColor = FitnessAppTheme.colors.White,
            contentColor = FitnessAppTheme.colors.Black,
            elevation = 4.dp
        )
    })
}