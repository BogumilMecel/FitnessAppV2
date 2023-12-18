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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.SummaryViewModel
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation.components.LogStreakSection

@Composable
fun SummaryScreen(
    viewModel: SummaryViewModel = hiltViewModel()
) {

    val state = viewModel.summaryState.collectAsState().value

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true){
        viewModel.errorState.collect{

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
                    .padding(15.dp),
                streak = state.logStreak
            )

        }
    }
}