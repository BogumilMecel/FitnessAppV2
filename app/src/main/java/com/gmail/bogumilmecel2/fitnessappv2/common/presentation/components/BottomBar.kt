package com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components

import androidx.compose.material.BottomNavigation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.util.BottomBarScreen
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BottomBarEvent
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BottomBarStatusProvider
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun BottomBar(
    navController: NavHostController,
    bottomBarStatusProvider: BottomBarStatusProvider
) {
    val screens = listOf(
        BottomBarScreen.Summary,
        BottomBarScreen.Diary,
        BottomBarScreen.Account,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var isActive by remember { mutableStateOf(true) }

    LaunchedEffect(
        key1 = true,
        block = {
            bottomBarStatusProvider.bottomBarEvent.receiveAsFlow().collectLatest {
                isActive = when(it) {
                    BottomBarEvent.Hide -> false
                    BottomBarEvent.Show -> true
                }
            }
        }
    )

    BottomNavigation(backgroundColor = FitnessAppTheme.colors.BackgroundSecondary) {
        screens.forEach { screen ->
            AddItem(
                bottomBarScreen = screen,
                screensNumber = screens.size,
                isActive = isActive,
                isSelected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true,
                onItemClicked = {
                    if (isActive) {
                        navController.navigate(screen.route)
                    } else {
                        bottomBarStatusProvider.onBottomBarClicked?.invoke()
                    }
                }
            )
        }
    }
}