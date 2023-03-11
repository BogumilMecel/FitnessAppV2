package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.presentation.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.DefaultCardBackground
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.PieChartWithMiddleText
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.domain.model.NutritionData

@Composable
fun ProductNutritionSection(
    nutritionData: NutritionData,
    modifier: Modifier
) {
    DefaultCardBackground(
        modifier = modifier,
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .padding(
                        bottom = 15.dp,
                        end = 15.dp,
                        top = 15.dp,
                        start = 5.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                PieChartWithMiddleText(
                    pieChartData = nutritionData.pieChartData,
                    middleText = stringResource(id = R.string.kcal_with_value, nutritionData.nutritionValues.calories),
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
    )
}