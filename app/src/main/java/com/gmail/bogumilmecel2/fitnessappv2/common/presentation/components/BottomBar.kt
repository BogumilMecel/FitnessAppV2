package com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components

import androidx.compose.material.BottomNavigation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.Grey
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.util.BottomBarScreen

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Summary,
        BottomBarScreen.Diary,
        BottomBarScreen.Account,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        backgroundColor = Grey
    ) {
        screens.forEach { screen ->
            AddItem(
                bottomBarScreen = screen,
                navController = navController,
                screensNumber = screens.size,
                isSelected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true
            )
        }
    }
}