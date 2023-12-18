package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import com.gmail.bogumilmecel2.ui.theme.LocalColor.DarkGreyElevation6

@Composable
fun SearchProductItem(
    weight: Int,
    unit: String,
    name: String,
    calories: Int,
    onItemClick: (() -> Unit)? = null,
    background: Color = DarkGreyElevation6
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(background).let { modifier ->
                onItemClick?.let { onClick ->
                    modifier.clickable {
                        onClick()
                    }
                } ?: modifier
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 10.dp,
                    horizontal = 15.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "${weight}${unit}",
                    style = MaterialTheme.typography.body2.copy(
                        color = FitnessAppTheme.colors.ContentSecondary
                    )
                )
            }

            Text(
                text = "$calories kcal",
                style = MaterialTheme.typography.body2.copy(
                    color = FitnessAppTheme.colors.ContentSecondary
                )
            )

        }
    }
}