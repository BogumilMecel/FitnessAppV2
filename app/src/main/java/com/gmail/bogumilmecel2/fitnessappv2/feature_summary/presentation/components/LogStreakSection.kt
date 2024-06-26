package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.components.DefaultCardBackground
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer

@Composable
fun LogStreakSection(
    streak: Int,
    modifier: Modifier
) {

    DefaultCardBackground(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.you_have_logged_for),
                style = MaterialTheme.typography.h3
            )

            HeightSpacer(4.dp)

            Text(
                text = String.format(stringResource(id = R.string.days), streak),
                style = MaterialTheme.typography.body2
            )
        }
    }
}