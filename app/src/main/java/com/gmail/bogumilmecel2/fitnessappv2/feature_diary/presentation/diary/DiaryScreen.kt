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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.BackHandler
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ViewModelLayout
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary.components.CalendarSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary.components.DiaryMealSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary.components.NutritionBottomSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.shared.ProductItemDialog
import com.gmail.bogumilmecel2.ui.theme.LocalColor.BackgroundSecondary
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun DiaryScreen(navigator: DestinationsNavigator) {
    hiltViewModel<DiaryViewModel>().ViewModelLayout(navigator = navigator) {
        val state by __state.collectAsStateWithLifecycle()
        BackHandler { onEvent(DiaryEvent.BackPressed) }

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
                        onDeleteButtonClicked = { onEvent(DiaryEvent.ClickedDeleteInDialog) },
                        onEditButtonClicked = { onEvent(DiaryEvent.ClickedEditInDialog) },
                        onDismissRequest = { onEvent(DiaryEvent.DismissedDialog) }
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
                        onArrowForwardClicked = { onEvent(event = DiaryEvent.ClickedCalendarArrowForward) },
                        onArrowBackwardsClicked = { onEvent(event = DiaryEvent.ClickedCalendarArrowBackwards) },
                    )

                    LazyColumn {
                        items(MealName.entries) { mealName ->
                            DiaryMealSection(
                                mealName = mealName,
                                diaryEntries = state.diaryEntries[mealName]?.diaryEntries,
                                nutritionValues = state.diaryEntries[mealName]?.nutritionValues,
                                wantedNutritionValues = state.wantedTotalNutritionValues,
                                onEvent = { event -> onEvent(event) }
                            )
                        }
                    }
                }
            }
        }
    }
}