package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product

@Composable
fun ProductMainSection(
    product: Product,
    currentWeight: String,
    onWeightEntered: (String) -> Unit,
    nutritionData: NutritionData
) {
    Column(
        modifier = Modifier
        .fillMaxWidth()
    ) {
        ProductNameSection(
            currentWeight = currentWeight,
            product = product,
            onWeightEntered = {
                onWeightEntered(it)
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        ProductNutritionSection(
            nutritionData = nutritionData,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
