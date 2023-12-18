package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.SummaryEvent
import com.gmail.bogumilmecel2.ui.components.complex.FloatNumberPicker

@SuppressLint(
    "DiscouragedPrivateApi",
    "ResourceType"
)
@Composable
fun WeightPickerDialog(
    onEvent: (SummaryEvent) -> Unit,
    startingValue: Float
) {
    var currentValue by remember {
        mutableFloatStateOf(startingValue)
    }

    Dialog(
        onDismissRequest = {
            onEvent(SummaryEvent.DismissedWeightPickerDialog)
        },
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.summary_weight_dialog_title),
                    style = MaterialTheme.typography.h3
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                FloatNumberPicker(
                    modifier = Modifier.weight(1f),
                    value = currentValue,
                    minValue = startingValue - 50f,
                    maxValue = startingValue + 50f,
                    onValueChange = {
                        currentValue = it
                    }
                )

                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                ) {
                    Button(
                        onClick = {
                            onEvent(SummaryEvent.SavedWeightPickerValue(value = currentValue.toDouble()))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = stringResource(id = R.string.save))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = {
                            onEvent(SummaryEvent.DismissedWeightPickerDialog)
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = androidx.compose.ui.graphics.Color.Transparent,
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colors.primary
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.cancel),
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        }

    }
}