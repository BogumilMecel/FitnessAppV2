package com.gmail.bogumilmecel2.fitnessappv2.feature_training.presentation.training

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ViewModelLayout
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary.components.CalendarSection
import com.gmail.bogumilmecel2.ui.components.base.FullHeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.components.base.TextWithTopIcon
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun TrainingScreen(navigator: DestinationsNavigator) {
    hiltViewModel<TrainingViewModel>().ViewModelLayout(navigator = navigator) {
        val state by __state.collectAsStateWithLifecycle()
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
                    onEvent(event = TrainingEvent.ClickedCalendarArrowForward)
                },
                onArrowBackwardsClicked = {
                    onEvent(event = TrainingEvent.ClickedCalendarArrowBackwards)
                },
            )

            FullHeightSpacer()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextWithTopIcon(
                    modifier = Modifier
                        .clip(FitnessAppTheme.shapes.Medium)
                        .clickable { onEvent(TrainingEvent.ClickedAddButton) }
                        .padding(12.dp),
                    iconModifier = Modifier.size(32.dp),
                    icon = IconVector.Add,
                    text = stringResource(id = R.string.training_start_workout)
                )

                TextWithTopIcon(
                    modifier = Modifier
                        .clip(FitnessAppTheme.shapes.Medium)
                        .clickable { }
                        .padding(12.dp),
                    icon = IconVector.Copy,
                    text = stringResource(id = R.string.training_copy_workout)
                )
            }
        }
    }
}