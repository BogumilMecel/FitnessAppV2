package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.diary

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.DarkGreyElevation3
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.Grey
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.diary.components.CalendarSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.diary.components.DiaryMealSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.diary.components.NutritionBottomSection

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun DiaryScreen(
    viewModel: DiaryViewModel = hiltViewModel(),
    paddingValues: PaddingValues
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.errorState.collect {
            scaffoldState.snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
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
                            text = state.longClickedDiaryEntry?.let {
                                state.longClickedDiaryEntry.product.name +
                                        " (${state.longClickedDiaryEntry.weight}" +
                                        "${
                                            state.longClickedDiaryEntry.product.unit
                                        })"
                            } ?: stringResource(id = R.string.product)
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

                LazyColumn{
                    items(state.meals.size){
                        DiaryMealSection(
                            meal = state.meals[it],
                            onEvent = {event ->
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
}