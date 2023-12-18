package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.SummaryViewModel
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation.components.CaloriesSumSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation.components.LogStreakSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation.components.WeightPickerDialog
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation.components.WeightSection

@Composable
fun SummaryScreen(
    viewModel: SummaryViewModel = hiltViewModel()
) {
    val state = viewModel.summaryState.collectAsState().value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true){
        viewModel.errorState.collect{
            scaffoldState.snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        if (state.isWeightPickerVisible){
            WeightPickerDialog(
                onEvent = {
                    viewModel.onEvent(it)
                },
                startingValue = 100.0
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            LogStreakSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                streak = state.logStreak ?: 1
            )

            Spacer(modifier = Modifier.height(16.dp))

            CaloriesSumSection(
                currentCalories = state.caloriesSum ?: 0,
                wantedCalories = state.wantedCalories,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            WeightSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                lastWeightEntry = state.weightEntries.sortedByDescending { it.timestamp }.getOrNull(0)?.value,
                weightProgress = state.weightProgress,
                onEvent = {
                    viewModel.onEvent(it)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}