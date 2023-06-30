package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.gmail.bogumilmecel2.ui.components.base.IconButtonParams
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun WeightSection(
    modifier: Modifier,
    lastWeightEntry: Double?,
    weightProgress: String?,
    buttonActive: Boolean,
    onEvent: (SummaryEvent) -> Unit
) {
    DefaultCardBackground(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier

            ) {
                Text(
                    text = stringResource(id = R.string.weight),
                    style = MaterialTheme.typography.h3
                )

                Spacer(modifier = Modifier.height(4.dp))

                if (lastWeightEntry != null) {
                    Text(
                        text = "${lastWeightEntry}kg",
                        style = MaterialTheme.typography.body2
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (weightProgress != null) {
                    Text(
                        text = "${weightProgress}kg",
                        style = MaterialTheme.typography.body2.copy(
                            color = FitnessAppTheme.colors.ContentSecondary
                        )
                    )
                }

                Spacer(modifier = Modifier.width(24.dp))

                CustomIconButton(
                    params = IconButtonParams(
                        iconVector = IconVector.Add,
                        enabled = buttonActive,
                        onClick = {
                            onEvent(SummaryEvent.ClickedAddWeightEntryButton)
                        }
                    ),
                )

                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}