package com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.util.BottomBarScreen

@Composable
fun RowScope.AddItem(
    bottomBarScreen: BottomBarScreen,
    currentDestination:NavDestination?,
    navController:NavHostController,
    screensNumber: Int
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            modifier = Modifier
                .width(LocalConfiguration.current.screenWidthDp.dp/screensNumber),
            color = if (currentDestination?.hierarchy?.any {
                    it.route == bottomBarScreen.route
                } == true) Color.White else Color.Transparent
        )

        this@AddItem.BottomNavigationItem(
            label = {
                Text(text = bottomBarScreen.title)
            },
            icon = {
                Icon(imageVector = bottomBarScreen.icon, contentDescription = bottomBarScreen.title)
            },
            selected = currentDestination?.hierarchy?.any {
                it.route == bottomBarScreen.route
            } == true,
            onClick = {
                navController.navigate(bottomBarScreen.route)
            }

        )
    }
}