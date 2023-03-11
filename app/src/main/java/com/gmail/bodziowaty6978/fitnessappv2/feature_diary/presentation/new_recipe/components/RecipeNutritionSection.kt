package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.DefaultCardBackground
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.PieChartWithMiddleText
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.LightGreen1
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.LightGreen3
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.TextGrey
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Price
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.util.SelectedNutritionType
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.domain.model.NutritionData
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.presentation.components.NutritionValuesList
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.presentation.components.PriceRows

@Composable
fun RecipeNutritionSection(
    modifier: Modifier,
    nutritionData: NutritionData,
    selectedNutritionType: SelectedNutritionType,
    onSelectedNutritionType: (SelectedNutritionType) -> Unit,
    price: Price?
) {
    DefaultCardBackground(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 15.dp, top = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 15.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recipe information",
                        style = MaterialTheme.typography.h3
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "serving",
                            style = MaterialTheme.typography.body2.copy(
                                color = TextGrey
                            )
                        )
                        Switch(
                            checked = selectedNutritionType is SelectedNutritionType.Recipe,
                            onCheckedChange = {
                                onSelectedNutritionType(if (it) SelectedNutritionType.Recipe else SelectedNutritionType.Serving)
                            },
                            colors = SwitchDefaults.colors(
                                uncheckedThumbColor = LightGreen1,
                                uncheckedTrackColor = LightGreen3
                            )
                        )
                        Text(
                            text = "recipe",
                            style = MaterialTheme.typography.body2.copy(
                                color = TextGrey
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

            Spacer(modifier = Modifier.height(6.dp))

            if (price != null && price.value != 0.0){
                PriceRows(
                    price = price,
                    nutritionValues = nutritionData.nutritionValues
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Prices:",
                            style = MaterialTheme.typography.body1
                        )

                        Spacer(modifier = Modifier.width(3.dp))

                        Text(
                            text = "N/A",
                            style = MaterialTheme.typography.h3
                        )
                    }
                    Text(
                        text = "Not every product has a price",
                        style = MaterialTheme.typography.body2.copy(
                            color = TextGrey
                        )
                    )
                }
            }
        }
    }
}