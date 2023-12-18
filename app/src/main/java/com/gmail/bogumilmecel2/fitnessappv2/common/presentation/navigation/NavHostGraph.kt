package com.gmail.bogumilmecel2.fitnessappv2.common.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gmail.bogumilmecel2.fitnessappv2.NavGraphs
import com.gmail.bogumilmecel2.fitnessappv2.common.data.singleton.TokenStatus
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.navigation.NavigationAction
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.navigation.Navigator
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
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import com.gmail.bogumilmecel2.ui.theme.LocalColor.DarkGreyElevation1
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun NavHostGraph(
    navController: NavHostController = rememberNavController(),
    navigator: Navigator,
    startDestination: Destination = SplashScreenDestination
) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = if (currentDestination == SearchScreenDestination.route) DarkGreyElevation1 else FitnessAppTheme.colors.Background)

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(true) {
        navigator.navActions.receiveAsFlow().collect {
            it?.let {
                if (it.direction.route == "navigate_up") {
                    navController.navigateUp()
                } else if (it.navOptions.popUpToRoute == "pop_up" && navController.currentDestination?.route != null) {
                    navController.navigate(it.direction.route) {
                        navController.currentDestination?.route?.let { currentRoute ->
                            popUpTo(currentRoute) {
                                inclusive = true
                            }
                        }
                    }
                } else {
                    navController.navigate(
                        it.direction.route,
                        it.navOptions
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        TokenStatus.tokenStatus.receiveAsFlow().collect {
            if (it == TokenStatus.Status.UNAVAILABLE) {
                navigator.navigate(
                    navAction = NavigationAction(
                        direction = LoginScreenDestination,
                        navOptions = NavOptions.Builder().setPopUpTo(
                            0,
                            true
                        ).build()
                    )
                )
            }
        }
    }

    val bottomBarScreens = listOf(
        AccountScreenDestination.route,
        SummaryScreenDestination.route,
        DiaryScreenDestination.route
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
            color = MaterialTheme.colors.background
        ) {
            DestinationsNavHost(
                navGraph = NavGraphs.root,
                navController = navController,
                startRoute = startDestination
            )
        }
    }
}