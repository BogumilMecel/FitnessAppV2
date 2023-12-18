package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.datastoreNutrition
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.SummaryViewModel
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation.components.CaloriesSumSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation.components.LogStreakSection

@Composable
fun SummaryScreen(
    viewModel: SummaryViewModel = hiltViewModel()
) {
    val state = viewModel.summaryState.collectAsState().value
    val scaffoldState = rememberScaffoldState()

    val wantedCalories = LocalContext.current.datastoreNutrition.data.collectAsState(initial = NutritionValues()).value.calories

    LaunchedEffect(key1 = true){
        viewModel.errorState.collect{
            scaffoldState.snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            LogStreakSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 8.dp) ,
                streak = state.logStreak ?: 0
            )

            CaloriesSumSection(
                currentCalories = state.caloriesSum ?: 0,
                wantedCalories = wantedCalories,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 8.dp)
            )

        }
    }
}