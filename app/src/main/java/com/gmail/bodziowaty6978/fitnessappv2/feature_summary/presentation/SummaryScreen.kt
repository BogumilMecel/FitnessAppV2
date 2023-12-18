package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.BackHandler
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

    val activity = (LocalContext.current as? Activity)

    BackHandler {
        activity?.finish()
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
                lastWeightEntry = state.weightEntries.sortedByDescending { it.timestamp }.getOrNull(0)?.value,
                weightProgress = state.weightProgress,
                onEvent = {
                    viewModel.onEvent(it)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

        }
    }
}