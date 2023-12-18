package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.edit_nutrition_goals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ConfigureViewModel
import com.gmail.bogumilmecel2.fitnessappv2.components.DefaultCardBackground
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.edit_nutrition_goals.components.MacroElementsSection
import com.gmail.bogumilmecel2.ui.components.base.CustomBasicTextField
import com.gmail.bogumilmecel2.ui.components.complex.HeaderRow
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun EditNutritionGoalsScreen(
    viewModel: EditNutritionGoalsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    viewModel.ConfigureViewModel(navigator = navigator)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HeaderRow(
            middlePrimaryText = stringResource(id = R.string.nutrition_goals),
            onBackPressed = {
                viewModel.onEvent(EditNutritionGoalsEvent.BackArrowPressed)
            }
        )

        Spacer(modifier = Modifier.height(6.dp))

        DefaultCardBackground(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
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
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = stringResource(id = R.string.kcal),
                        style = MaterialTheme.typography.body2.copy(
                            color = FitnessAppTheme.colors.ContentSecondary
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