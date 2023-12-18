package com.gmail.bogumilmecel2.fitnessappv2.feature_training.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ViewModelLayout
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary.components.CalendarSection
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun TrainingScreen(navigator: DestinationsNavigator) {
    hiltViewModel<TrainingViewModel>().ViewModelLayout(navigator = navigator) { viewModel, state ->
        Column {
            CalendarSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 10.dp,
                        vertical = 2.dp
                    ),
                date = state.currentDate,
                onArrowForwardClicked = {
                    viewModel.onEvent(event = TrainingEvent.ClickedCalendarArrowForward)
                },
                onArrowBackwardsClicked = {
                    viewModel.onEvent(event = TrainingEvent.ClickedCalendarArrowBackwards)
                },
            )
        }
    }
}