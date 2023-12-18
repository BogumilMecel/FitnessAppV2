package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.PieChartWithMiddleText
import com.gmail.bogumilmecel2.fitnessappv2.components.DefaultCardBackground
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.util.SelectedNutritionType
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.domain.model.NutritionData
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.components.NutritionValuesList
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import com.gmail.bogumilmecel2.ui.theme.LocalColor.LightGreen3
import com.gmail.bogumilmecel2.ui.theme.LocalColor.Quaternary

@Composable
fun RecipeNutritionSection(
    modifier: Modifier,
    nutritionData: NutritionData,
    selectedNutritionType: SelectedNutritionType,
    onSelectedNutritionType: (SelectedNutritionType) -> Unit,
) {
    DefaultCardBackground(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .padding(
                    bottom = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.recipe_information),
                    style = MaterialTheme.typography.h3,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.serving).lowercase(),
                        style = MaterialTheme.typography.body2.copy(
                            color = FitnessAppTheme.colors.ContentSecondary
                        )
                    )
                    Switch(
                        checked = selectedNutritionType is SelectedNutritionType.Recipe,
                        onCheckedChange = {
                            onSelectedNutritionType(if (it) SelectedNutritionType.Recipe else SelectedNutritionType.Serving)
                        },
                        colors = SwitchDefaults.colors(
                            uncheckedThumbColor = Quaternary,
                            uncheckedTrackColor = LightGreen3
                        )
                    )
                    Text(
                        text = stringResource(id = R.string.recipe).lowercase(),
                        style = MaterialTheme.typography.body2.copy(
                            color = FitnessAppTheme.colors.ContentSecondary
                        )
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                PieChartWithMiddleText(
                    pieChartData = nutritionData.pieChartData,
                    middleText = stringResource(
                        id = R.string.kcal_with_value,
                        nutritionData.nutritionValues.calories
                    ),
                    modifier = Modifier
                        .weight(0.4F)
                        .fillMaxHeight()
                )

                NutritionValuesList(
                    modifier = Modifier
                        .weight(0.8F)
                        .fillMaxHeight(),
                    nutritionValues = nutritionData.nutritionValues
                )
            }
        }
    }
}