package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.BackHandler
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.WidthSpacer
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.DarkGreyElevation6
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.Grey
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.TextGrey
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary.components.CalendarSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary.components.DiaryMealSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary.components.NutritionBottomSection
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun DiaryScreen(
    viewModel: DiaryViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 = true) {
        viewModel.initData()
    }

    BackHandler {
        viewModel.onEvent(DiaryEvent.BackPressed)
    }

    Scaffold(
        bottomBar = {
            NutritionBottomSection(
                currentNutritionValues = state.currentTotalNutritionValues,
                wantedNutritionValues = state.wantedTotalNutritionValues,
                modifier = Modifier
                    .background(Grey)
                    .padding(bottom = 5.dp)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (state.isDialogShowed) {
                androidx.compose.material3.AlertDialog(
                    content = {
                        Surface(
                            modifier = Modifier.wrapContentSize(),
                            shape = RoundedCornerShape(20.dp),
                            color = DarkGreyElevation6
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = CenterVertically
                                ) {
                                    state.longClickedDiaryItem?.let { diaryItem ->
                                        Text(
                                            text = when (diaryItem) {
                                                    is ProductDiaryEntry -> diaryItem.product.name
                                                    is RecipeDiaryEntry -> diaryItem.recipe.name
                                                    else -> ""
                                                },
                                            style = MaterialTheme.typography.h3
                                        )

                                        WidthSpacer(width = 2.dp)

                                        Text(
                                            text = "(${diaryItem.getDisplayValue()})",
                                            style = MaterialTheme.typography.body2,
                                            color = TextGrey
                                        )
                                    }
                                }


                                HeightSpacer(height = 2.dp)

                                Text(text = "What would you like to do:", style = MaterialTheme.typography.body2)

                                HeightSpacer(height = 24.dp)

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            viewModel.onEvent(DiaryEvent.ClickedDeleteInDialog)
                                        },
                                        modifier = Modifier.weight(1F),
                                        border = BorderStroke(
                                            1.dp,
                                            MaterialTheme.colors.primary
                                        ),
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

                                    WidthSpacer(width = 8.dp)

                                    Button(
                                        onClick = {
                                            viewModel.onEvent(DiaryEvent.ClickedEditInDialog)
                                        },
                                        modifier = Modifier
                                            .weight(1F)
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.edit).uppercase(),
                                            style = MaterialTheme.typography.button
                                        )
                                    }
                                }
                            }
                        }
                    },
                    onDismissRequest = {
                        viewModel.onEvent(DiaryEvent.DismissedDialog)
                    },
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = paddingValues.calculateBottomPadding()),
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
                        .padding(
                            horizontal = 10.dp,
                            vertical = 2.dp
                        )
                )

                val mealNames = MealName.values()

                LazyColumn {
                    items(mealNames.size) {
                        DiaryMealSection(
                            mealName = mealNames[it],
                            diaryEntries = state.getDiaryEntries(mealName = mealNames[it]),
                            nutritionValues = state.mealNutritionValues[mealNames[it]],
                            wantedNutritionValues = state.wantedTotalNutritionValues,
                            onEvent = { event ->
                                viewModel.onEvent(event)
                            }
                        )
                    }
                }
            }
        }
    }
}