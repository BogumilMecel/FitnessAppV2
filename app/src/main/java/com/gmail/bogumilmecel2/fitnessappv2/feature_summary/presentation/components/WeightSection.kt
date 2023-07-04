package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.components.DefaultCardBackground
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.SummaryEvent
import com.gmail.bogumilmecel2.ui.components.base.CustomIconButton
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.IconButtonParams
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun WeightSection(
    modifier: Modifier,
    lastWeightEntry: Double?,
    weightProgress: String?,
    onEvent: (SummaryEvent) -> Unit
) {
    DefaultCardBackground(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                Text(
                    text = stringResource(id = R.string.weight),
                    style = MaterialTheme.typography.h3
                )

                HeightSpacer(4.dp)

                if (lastWeightEntry != null) {
                    Text(
                        text = "${lastWeightEntry}kg",
                        style = MaterialTheme.typography.body2
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (weightProgress != null) {
                    Text(
                        text = "${weightProgress}kg",
                        style = MaterialTheme.typography.body2.copy(
                            color = FitnessAppTheme.colors.ContentSecondary
                        )
                    )
                }

                HeightSpacer(24.dp)

                CustomIconButton(
                    params = IconButtonParams(
                        iconVector = IconVector.Add,
                        onClick = {
                            onEvent(SummaryEvent.ClickedAddWeightEntryButton)
                        }
                    ),
                )

                HeightSpacer(16.dp)
            }
        }
    }
}