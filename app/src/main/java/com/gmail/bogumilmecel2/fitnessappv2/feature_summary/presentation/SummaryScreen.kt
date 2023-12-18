package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.BackHandler
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components.CaloriesSumSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components.LogStreakSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components.WeightPickerDialog
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components.WeightSection
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun SummaryScreen(
    viewModel: SummaryViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    val activity = (LocalContext.current as? Activity)

    LaunchedEffect(key1 = true) {
        viewModel.initializeData()
    }

    BackHandler {
        activity?.finish()
    }

    if (state.isWeightPickerVisible) {
        WeightPickerDialog(
            onEvent = {
                viewModel.onEvent(it)
            },
            startingValue = 100.0f
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        LogStreakSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            streak = state.logStreak ?: 1
        )

        Spacer(modifier = Modifier.height(12.dp))

        CaloriesSumSection(
            currentCalories = state.caloriesSum ?: 0,
            wantedCalories = state.wantedCalories,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        WeightSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            lastWeightEntry = state.latestWeightEntry?.value,
            weightProgress = state.weightProgress,
            onEvent = {
                viewModel.onEvent(it)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

    }
}