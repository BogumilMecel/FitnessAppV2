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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.LightGreen1
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.LightGreen3
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.TextGrey
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Price
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.util.SelectedNutritionType
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components.ChartSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components.NutritionData
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components.NutritionValuesList
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components.PriceRows

@Composable
fun RecipeNutritionSection(
    modifier: Modifier,
    nutritionData: NutritionData,
    selectedNutritionType: SelectedNutritionType,
    onSelectedNutritionType: (SelectedNutritionType) -> Unit,
    price: Price?
) {
    Card(
        modifier = modifier,
        elevation = 3.dp,
        shape = RoundedCornerShape(10)
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
                    ChartSection(
                        nutritionData = nutritionData,
                        modifier = Modifier.weight(0.4F)
                    )

                    NutritionValuesList(
                        modifier = Modifier
                            .weight(0.8F)
                            .fillMaxHeight(),
                        nutritionValues = nutritionData.nutritionValues
                    )
                }
            }

            price?.let {
                Spacer(modifier = Modifier.height(6.dp))

                PriceRows(
                    price = price,
                    nutritionValues = nutritionData.nutritionValues
                )
            }
        }
    }
}