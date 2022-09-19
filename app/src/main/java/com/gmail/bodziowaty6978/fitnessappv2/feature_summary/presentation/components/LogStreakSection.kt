package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.R

@Composable
fun LogStreakSection(
    streak:Int?,
    modifier: Modifier
) {
    
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(15)
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
        ) {
            Text(
                text = stringResource(id = R.string.you_have_logged_for),
                style = MaterialTheme.typography.h3
            )

            streak?.let {
                Text(
                    text = String.format(stringResource(id = R.string.days), streak),
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}