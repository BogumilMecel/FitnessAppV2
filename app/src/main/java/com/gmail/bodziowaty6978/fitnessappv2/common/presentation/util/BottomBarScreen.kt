package com.gmail.bodziowaty6978.fitnessappv2.common.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.gmail.bodziowaty6978.fitnessappv2.destinations.AccountScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.destinations.DiaryScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.destinations.SummaryScreenDestination

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Summary : BottomBarScreen(
        route = SummaryScreenDestination.route,
        title = "Summary",
        icon = Icons.Default.Home
    )
    object Diary : BottomBarScreen(
        route = DiaryScreenDestination.route,
        title = "Diary",
        icon = Icons.Default.Book
    )
    object Account : BottomBarScreen(
        route = AccountScreenDestination.route,
        title = "Account",
        icon = Icons.Default.AccountCircle
    )
}