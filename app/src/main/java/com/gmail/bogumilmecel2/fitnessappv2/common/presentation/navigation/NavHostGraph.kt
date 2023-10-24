package com.gmail.bogumilmecel2.fitnessappv2.common.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gmail.bogumilmecel2.fitnessappv2.NavGraphs
import com.gmail.bogumilmecel2.fitnessappv2.common.data.singleton.TokenStatus
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.BottomBar
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.CustomSnackbar
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ErrorUtils
import com.gmail.bogumilmecel2.fitnessappv2.destinations.AccountScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.Destination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.DiaryScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.LoginScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SearchScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SplashScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SummaryScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.TrainingScreenDestination
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import kotlinx.coroutines.flow.receiveAsFlow

@OptIn(
    ExperimentalMaterialNavigationApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun NavHostGraph(
    navController: NavHostController,
    startDestination: Destination = SplashScreenDestination,
) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = if (currentDestination == SearchScreenDestination.route) FitnessAppTheme.colors.BackgroundSecondary
        else FitnessAppTheme.colors.BackgroundPrimary
    )

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        TokenStatus.tokenStatus.receiveAsFlow().collect {
            if (it == TokenStatus.Status.UNAVAILABLE) {
                navController.navigate(
                    route = LoginScreenDestination.route,
                    navOptions = NavOptions.Builder().setPopUpTo(
                        0,
                        true
                    ).build(),
                )
            }
        }
    }

    val bottomBarScreens = listOf(
        AccountScreenDestination.route,
        SummaryScreenDestination.route,
        DiaryScreenDestination.route,
        TrainingScreenDestination.route
    )

    LaunchedEffect(key1 = true) {
        ErrorUtils.errorState.receiveAsFlow().collect {
            scaffoldState.snackbarHostState.showSnackbar(
                message = it
            )
        }
    }

    Scaffold(
        bottomBar = {
            if (bottomBarScreens.contains(currentDestination)) {
                BottomBar(navController = navController)
            }
        },
        scaffoldState = scaffoldState,
        snackbarHost = { hostState ->
            CustomSnackbar(snackbarHostState = hostState)
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = FitnessAppTheme.colors.BackgroundPrimary
        ) {
            DestinationsNavHost(
                navGraph = NavGraphs.root,
                navController = navController,
                startRoute = startDestination,
                engine = rememberAnimatedNavHostEngine(
                    rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING
                )
            )
        }
    }
}