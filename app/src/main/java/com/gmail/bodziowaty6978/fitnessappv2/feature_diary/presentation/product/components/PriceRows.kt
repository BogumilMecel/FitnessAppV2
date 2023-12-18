package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.round
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Price

@Composable
fun PriceRows(
    price: Price,
    nutritionValues: NutritionValues
) {
    Text(
        text = stringResource(id = R.string.prices),
        style = MaterialTheme.typography.body1,
        modifier = Modifier
            .padding(top = 15.dp, start = 15.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))

    val currency = stringResource(id = price.currency.getDisplayValue())

    PriceRow(
        name = stringResource(id = R.string.price_for_100_kcal),
        value = (price.value / nutritionValues.calories.toDouble() * 100.0).round(2),
        currency = currency,
        index = 1
    )

    PriceRow(
        name = stringResource(id = R.string.price_for_100g_of_carbohydrates),
        value = (price.value / nutritionValues.carbohydrates * 100.0).round(2),
        currency = currency,
        index = 2
    )

    PriceRow(
        name = stringResource(id = R.string.price_for_100g_of_protein),
        value = (price.value / nutritionValues.protein * 100.0).round(2),
        currency = currency,
        index = 3
    )

    PriceRow(
        name = stringResource(id = R.string.price_for_100g_of_fat),
        value = (price.value / nutritionValues.fat * 100.0).round(2),
        currency = currency,
        index = 4
    )
}