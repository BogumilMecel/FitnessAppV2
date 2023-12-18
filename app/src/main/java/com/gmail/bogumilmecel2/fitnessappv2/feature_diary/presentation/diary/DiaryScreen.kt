package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.BackHandler
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ConfigureViewModel
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary.components.CalendarSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary.components.DiaryMealSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary.components.NutritionBottomSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.shared.ProductItemDialog
import com.gmail.bogumilmecel2.ui.theme.LocalColor.BackgroundSecondary
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun DiaryScreen(viewModel: DiaryViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    viewModel.ConfigureViewModel()

    BackHandler {
        viewModel.onEvent(DiaryEvent.BackPressed)
    }

    Scaffold(
        bottomBar = {
            NutritionBottomSection(
                currentNutritionValues = state.currentTotalNutritionValues,
                wantedNutritionValues = state.wantedTotalNutritionValues,
                modifier = Modifier
                    .background(BackgroundSecondary)
                    .padding(bottom = 5.dp)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            state.longClickedDiaryItemParams?.let {
                ProductItemDialog(
                    title = it.dialogTitle,
                    onDeleteButtonClicked = {
                        viewModel.onEvent(DiaryEvent.ClickedDeleteInDialog)
                    },
                    onEditButtonClicked = {
                        viewModel.onEvent(DiaryEvent.ClickedEditInDialog)
                    },
                    onDismissRequest = {
                        viewModel.onEvent(DiaryEvent.DismissedDialog)
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = paddingValues.calculateBottomPadding()),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                CalendarSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background)
                        .padding(
                            horizontal = 10.dp,
                            vertical = 2.dp
                        ),
                    date = state.displayedDate,
                    onArrowForwardClicked = {
                        viewModel.onEvent(event = DiaryEvent.ClickedCalendarArrowForward)
                    },
                    onArrowBackwardsClicked = {
                        viewModel.onEvent(event = DiaryEvent.ClickedCalendarArrowBackwards)
                    },
                )

                LazyColumn {
                    items(MealName.values()) { mealName ->
                        DiaryMealSection(
                            mealName = mealName,
                            diaryEntries = state.diaryEntries[mealName]?.diaryEntries,
                            nutritionValues = state.diaryEntries[mealName]?.nutritionValues,
                            wantedNutritionValues = state.wantedTotalNutritionValues,
                            onEvent = { event -> viewModel.onEvent(event) }
                        )
                    }
                }
            }
        }
    }
}