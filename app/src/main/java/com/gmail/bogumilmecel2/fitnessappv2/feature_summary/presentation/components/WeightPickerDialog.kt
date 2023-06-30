package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.SummaryEvent
import com.gmail.bogumilmecel2.ui.components.base.ButtonStyle
import com.gmail.bogumilmecel2.ui.components.base.CustomButton
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.complex.FloatNumberPicker
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

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

    Column(
        modifier = Modifier
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.summary_weight_dialog_title),
            style = FitnessAppTheme.typography.HeaderLarge
        )

        HeightSpacer(height = 16.dp)

        FloatNumberPicker(
            modifier = Modifier,
            value = currentValue,
            minValue = startingValue - 50f,
            maxValue = startingValue + 50f,
            onValueChange = {
                currentValue = it
            }
        )

        HeightSpacer(height = 16.dp)

        Column {
            CustomButton(
                text = stringResource(id = R.string.save),
                onClick = {
                    onEvent(SummaryEvent.SavedWeightPickerValue(value = currentValue.toDouble()))
                },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomButton(
                text = stringResource(id = R.string.cancel),
                onClick = {
                    onEvent(SummaryEvent.DismissedWeightPickerDialog)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                buttonStyle = ButtonStyle.OutlinedPrimaryButton
            )
        }
    }
}