package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.domain.model.NutritionData

@Composable
fun ProductMainSection(
    modifier: Modifier = Modifier,
    productName: String,
    currentWeight: String,
    onWeightEntered: (String) -> Unit,
    nutritionData: NutritionData
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        ProductNameSection(
            currentWeight = currentWeight,
            productName = productName,
            onWeightEntered = {
                onWeightEntered(it)
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        ProductNutritionSection(
            nutritionData = nutritionData,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
