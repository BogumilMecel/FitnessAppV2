package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.edit_nutrition_goals.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.ListItemPicker
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun NutritionPercentagePicker(
    nutritionName: String,
    nutritionValue: String,
    percentageValue: String,
    nutritionValueColor: Color,
    onPercentageValueChanged: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = nutritionName,
            style = MaterialTheme.typography.body2.copy(
                color = FitnessAppTheme.colors.ContentSecondary
            )
        )

        Text(
            text = "${nutritionValue}g",
            style = MaterialTheme.typography.h4.copy(
                color = nutritionValueColor
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        ListItemPicker(
            value = percentageValue,
            onValueChange = {
                onPercentageValueChanged(it)
            },
            list = (0..100).toList().map {
                "$it%"
            }
        )
    }
}