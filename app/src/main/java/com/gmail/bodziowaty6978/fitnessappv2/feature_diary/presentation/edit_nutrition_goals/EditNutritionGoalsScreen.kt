package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.edit_nutrition_goals

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.BackArrow
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.CustomBasicTextField
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.*
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.edit_nutrition_goals.components.MacroElementsSection

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun EditNutritionGoalsScreen(
    viewModel: EditNutritionGoalsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            BackArrow(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                viewModel.onEvent(EditNutritionGoalsEvent.BackArrowPressed)
            }

            Text(
                text = stringResource(id = R.string.nutrition_goals),
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = 3.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.calories),
                    style = MaterialTheme.typography.h3
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomBasicTextField(
                        value = state.calories,
                        onValueChange = {
                            viewModel.onEvent(EditNutritionGoalsEvent.EnteredCalories(it))
                        },
                        modifier = Modifier.width(80.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.body1.copy(
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = stringResource(id = R.string.kcal),
                        style = MaterialTheme.typography.body2.copy(
                            color = TextGrey
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        MacroElementsSection(
            modifier = Modifier.padding(horizontal = 10.dp),
            state = state,
            onEvent = {
                viewModel.onEvent(it)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                viewModel.onEvent(EditNutritionGoalsEvent.SaveButtonClicked)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = stringResource(id = R.string.save).uppercase())
        }

    }
}