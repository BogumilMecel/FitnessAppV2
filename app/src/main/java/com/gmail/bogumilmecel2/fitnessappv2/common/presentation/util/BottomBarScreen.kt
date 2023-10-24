package com.gmail.bogumilmecel2.fitnessappv2.common.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.destinations.AccountScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.DiaryScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SummaryScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.TrainingScreenDestination

enum class BottomBarScreen(
    val route: String,
    val labelResId: Int,
    val icon: ImageVector
) {
    Summary(
        route = SummaryScreenDestination.route,
        labelResId = R.string.bottom_nav_summary,
        icon = Icons.Default.Home
    ),
    Diary(
        route = DiaryScreenDestination.route,
        labelResId = R.string.bottom_nav_diary,
        icon = Icons.Default.Book
    ),
    Training(
        route = TrainingScreenDestination.route,
        labelResId = R.string.bottom_nav_training,
        icon = Icons.Default.FitnessCenter
    ),
    Account(
        route = AccountScreenDestination.route,
        labelResId = R.string.bottom_nav_account,
        icon = Icons.Default.AccountCircle
    )
}