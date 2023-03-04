package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.DefaultCardBackground
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.TextGrey
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation.SummaryEvent

@Composable
fun WeightSection(
    modifier: Modifier,
    lastWeightEntry: Double?,
    weightProgress: String?,
    onEvent:(SummaryEvent) -> Unit
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

                if (lastWeightEntry != null){
                    Text(
                        text = "${lastWeightEntry}kg",
                        style = MaterialTheme.typography.body2
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (weightProgress != null){
                    Text(
                        text = "${weightProgress}kg",
                        style = MaterialTheme.typography.body2.copy(
                            color = TextGrey
                        )
                    )
                }

                Spacer(modifier = Modifier.width(24.dp))

                IconButton(
                    onClick = {
                        onEvent(SummaryEvent.ClickedAddWeightEntryButton)
                    },
                    modifier = Modifier
                        .clip(CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}