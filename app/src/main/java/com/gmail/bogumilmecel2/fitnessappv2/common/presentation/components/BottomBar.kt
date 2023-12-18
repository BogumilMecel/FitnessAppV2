package com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components

import androidx.compose.material.BottomNavigation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.util.BottomBarScreen
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun BottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(backgroundColor = FitnessAppTheme.colors.BackgroundSecondary) {
        BottomBarScreen.entries.forEach { screen ->
            AddItem(
                bottomBarScreen = screen,
                screensNumber = BottomBarScreen.entries.size,
                isSelected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true,
                onItemClicked = { navController.navigate(screen.route) }
            )
        }
    }
}