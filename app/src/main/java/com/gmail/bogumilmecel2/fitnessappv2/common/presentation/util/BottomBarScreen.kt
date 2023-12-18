package com.gmail.bogumilmecel2.fitnessappv2.common.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.destinations.AccountScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.DiaryScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SummaryScreenDestination

sealed class BottomBarScreen(
    val route: String,
    val labelResId: Int,
    val icon: ImageVector
) {
    data object Summary : BottomBarScreen(
        route = SummaryScreenDestination.route,
        labelResId = R.string.bottom_nav_summary,
        icon = Icons.Default.Home
    )

    data object Diary : BottomBarScreen(
        route = DiaryScreenDestination.route,
        labelResId = R.string.bottom_nav_diary,
        icon = Icons.Default.Book
    )

    data object Account : BottomBarScreen(
        route = AccountScreenDestination.route,
        labelResId = R.string.bottom_nav_account,
        icon = Icons.Default.AccountCircle
    )
}