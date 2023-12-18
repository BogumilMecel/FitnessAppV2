package com.gmail.bodziowaty6978.fitnessappv2.common.presentation.navigation

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
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gmail.bodziowaty6978.fitnessappv2.NavGraphs
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.BottomBar
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.CustomSnackbar
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.DarkGrey
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.DarkGreyElevation1
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ErrorUtils
import com.gmail.bodziowaty6978.fitnessappv2.destinations.AccountScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.destinations.DiaryScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.destinations.SearchScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.destinations.SummaryScreenDestination
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun NavHostGraph(
    navController: NavHostController = rememberNavController(),
    navigator: Navigator
) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = if (currentDestination == SearchScreenDestination.route) DarkGreyElevation1 else DarkGrey)

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(true) {
        navigator.navActions.receiveAsFlow().collect {
            it?.let {
                if (it.direction.route == "navigate_up") {
                    navController.navigateUp()
                } else {
                    navController.navigate(it.direction.route, it.navOptions)
                }
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
            DestinationsNavHost(navGraph = NavGraphs.root, navController = navController)
        }
    }


}