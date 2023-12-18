package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.theme.LocalColor.DarkGreyElevation3
import com.gmail.bogumilmecel2.ui.theme.LocalColor.DarkGreyElevation6

@Composable
fun PriceRow(
    name: String,
    value: Double,
    currency: String,
    index: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (index % 2 == 0) DarkGreyElevation3 else DarkGreyElevation6)
            .padding(vertical = 10.dp, horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.body1
        )
        Text(
            text = "$value$currency",
            style = MaterialTheme.typography.h3
        )
    }

}