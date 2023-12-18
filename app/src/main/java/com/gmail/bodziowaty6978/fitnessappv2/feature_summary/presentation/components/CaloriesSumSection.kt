package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.R

@Composable
fun CaloriesSumSection(
    currentCalories: Int,
    wantedCalories: Int,
    modifier: Modifier
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(15),
        elevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.calories),
                    style = MaterialTheme.typography.h3
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "$currentCalories/",
                        style = MaterialTheme.typography.h3
                    )
                    Text(
                        text = "$wantedCalories kcal",
                        style = MaterialTheme.typography.body2
                    )
                }
            }

            Spacer(modifier = Modifier.width(64.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val progress = currentCalories.toFloat()/wantedCalories.toFloat()
                Text(
                    text = "${(progress*100.0).toInt()}%",
                    style = MaterialTheme.typography.body2
                )

                Spacer(modifier = Modifier.height(4.dp))

                LinearProgressIndicator(
                    progress = progress
                )
            }
        }
    }
}