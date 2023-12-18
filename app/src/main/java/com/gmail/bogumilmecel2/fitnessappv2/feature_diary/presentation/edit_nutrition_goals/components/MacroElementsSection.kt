package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.edit_nutrition_goals.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.components.DefaultCardBackground
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.BlueViolet3
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.Green
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.LightGreen3
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.OrangeYellow3
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.RedError
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.TextGrey
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.edit_nutrition_goals.EditNutritionGoalsEvent
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.edit_nutrition_goals.EditNutritionGoalsState

@Composable
fun MacroElementsSection(
    modifier: Modifier,
    state:EditNutritionGoalsState,
    onEvent:(EditNutritionGoalsEvent) -> Unit,
) {
    DefaultCardBackground(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.macro_elements),
                style = MaterialTheme.typography.h3
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                NutritionPercentagePicker(
                    nutritionName = stringResource(id = R.string.carbohydrates),
                    nutritionValue = state.nutritionValues.carbohydrates.toString(),
                    nutritionValueColor = LightGreen3,
                    percentageValue = "${state.carbohydratesPercentageValue.toInt()}%",
                    onPercentageValueChanged = {
                        onEvent(
                            EditNutritionGoalsEvent.CarbohydratesPickerValueChanged(
                                it
                            )
                        )
                    }
                )

                NutritionPercentagePicker(
                    nutritionName = stringResource(id = R.string.protein),
                    nutritionValue = state.nutritionValues.protein.toString(),
                    nutritionValueColor = BlueViolet3,
                    percentageValue = "${state.proteinPercentageValue.toInt()}%",
                    onPercentageValueChanged = {
                        onEvent(EditNutritionGoalsEvent.ProteinPickerValueChanged(it))
                    }
                )

                NutritionPercentagePicker(
                    nutritionName = stringResource(id = R.string.fat),
                    nutritionValue = state.nutritionValues.fat.toString(),
                    nutritionValueColor = OrangeYellow3,
                    percentageValue = "${state.fatPercentageValue.toInt()}%",
                    onPercentageValueChanged = {
                        onEvent(EditNutritionGoalsEvent.FatPickerValueChanged(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.total),
                    style = MaterialTheme.typography.h3
                )

                Text(
                    text = "${state.totalPercentage.toInt()}%",
                    style = MaterialTheme.typography.h3.copy(
                        color = if (state.totalPercentage == 100F) Green else RedError
                    )
                )
            }
            Text(
                text = stringResource(id = R.string.your_total_has_to_be_at_100),
                style = MaterialTheme.typography.body2.copy(
                    color = TextGrey
                )
            )
        }
    }
}