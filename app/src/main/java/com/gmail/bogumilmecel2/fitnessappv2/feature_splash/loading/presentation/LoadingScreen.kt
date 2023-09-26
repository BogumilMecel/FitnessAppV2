package com.gmail.bogumilmecel2.fitnessappv2.feature_splash.loading.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ConfigureViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(navigator: DestinationsNavigator) {
    hiltViewModel<LoadingViewModel>().ConfigureViewModel(navigator = navigator)
}