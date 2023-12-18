package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProductNutritionSection(
    nutritionData: NutritionData,
    modifier: Modifier
) {

    Card(
        elevation = 3.dp,
        modifier = modifier,
        shape = RoundedCornerShape(15)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(bottom = 15.dp, end = 15.dp, top = 15.dp, start = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ChartSection(
                nutritionData = nutritionData,
                modifier = Modifier.weight(0.4F).fillMaxHeight()
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