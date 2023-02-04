package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.diary

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.BackHandler
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.DarkGreyElevation3
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.Grey
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.diary.components.CalendarSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.diary.components.DiaryMealSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.diary.components.NutritionBottomSection

@Composable
fun DiaryScreen(
    viewModel: DiaryViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 = true) {
        viewModel.initData()
    }

    BackHandler {
        viewModel.onEvent(DiaryEvent.BackPressed)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (state.isDialogShowed) {
            AlertDialog(
                backgroundColor = DarkGreyElevation3,
                shape = RoundedCornerShape(10),
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                onDismissRequest = {
                    viewModel.onEvent(DiaryEvent.DismissedDialog)
                },
                buttons = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        OutlinedButton(
                            onClick = {
                                viewModel.onEvent(DiaryEvent.ClickedDeleteInDialog)
                            },
                            modifier = Modifier
                                .weight(1F)
                                .padding(horizontal = 10.dp, vertical = 10.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                            colors = ButtonDefaults.outlinedButtonColors(
                                backgroundColor = Color.Transparent
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.delete).uppercase(),
                                style = MaterialTheme.typography.button.copy(
                                    color = MaterialTheme.colors.primary
                                )
                            )
                        }

                        Button(
                            onClick = {
                                viewModel.onEvent(DiaryEvent.ClickedEditInDialog)
                            },
                            modifier = Modifier
                                .weight(1F)
                                .padding(horizontal = 10.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.edit).uppercase(),
                                style = MaterialTheme.typography.button
                            )
                        }

                    }
                },
                title = {
                    Text(
                        text = state.longClickedDiaryItem?.let { diaryItem ->
                            when (diaryItem) {
                                is ProductDiaryEntry -> {
                                    diaryItem.product.name +
                                            " (${diaryItem.weight}" +
                                            "${
                                                diaryItem.product.unit
                                            })"
                                }

                                is RecipeDiaryEntry -> {
                                    diaryItem.recipe.name +
                                            " (${diaryItem.portions}" +
                                            "${
                                                stringResource(id = R.string.portions)
                                            })"
                                }

                                else -> null
                            }

                        } ?: ""
                    )
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CalendarSection(
                onArrowPressed = {
                    viewModel.onEvent(
                        event = DiaryEvent.ChangedDate
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.background)
                    .padding(horizontal = 10.dp, vertical = 2.dp)
            )

            LazyColumn {
                items(state.meals.size) {
                    DiaryMealSection(
                        meal = state.meals[it],
                        onEvent = { event ->
                            viewModel.onEvent(event)
                        }
                    )
                }
            }
        }
        NutritionBottomSection(
            meals = state.meals,
            wantedNutritionValues = state.wantedNutritionValues,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(Grey)
        )
    }
}