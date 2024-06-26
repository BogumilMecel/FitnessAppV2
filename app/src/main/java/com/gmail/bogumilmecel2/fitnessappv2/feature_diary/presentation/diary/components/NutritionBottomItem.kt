package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NutritionBottomItem(
    currentValue: Int,
    wantedValue: Int,
    name: String,
    modifier: Modifier,
    progressColor:Color = MaterialTheme.colors.primary
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = 10.dp)
    ) {

        Text(
            text = name,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(bottom = 3.dp, top = 3.dp)
        )

        val progress = currentValue.toFloat() / wantedValue.toFloat()

        LinearProgressIndicator(
            progress = if (!progress.isNaN()) progress else 0f,
            color = progressColor
        )

        Text(
            modifier = Modifier.padding(top = 3.dp),
            text = "$currentValue/$wantedValue",
            style = MaterialTheme.typography.body2,
        )

    }

}