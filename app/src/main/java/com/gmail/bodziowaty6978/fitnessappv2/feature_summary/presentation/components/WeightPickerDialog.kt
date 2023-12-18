package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation.components

import android.annotation.SuppressLint
import android.graphics.Color
import android.widget.EditText
import android.widget.NumberPicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.round
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation.SummaryEvent

@SuppressLint("DiscouragedPrivateApi", "ResourceType")
@Composable
fun WeightPickerDialog(
    onEvent:(SummaryEvent) -> Unit,
    startingValue:Double
) {
    var currentValue by remember{
        mutableStateOf(startingValue)
    }

    val formatToString: (Double) -> String = { "${it.round(1)} kg" }
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
                    text = "What is your today's weight",
                    style = MaterialTheme.typography.h3
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AndroidView(
                    factory = {
                        NumberPicker(it).apply {
                            setFormatter { value ->
                                formatToString(value.toDouble() * 0.1)
                            }
                            wrapSelectorWheel = false
                            setBackgroundResource(Color.argb(0, 0, 0, 0))

                            minValue = (10.0 / 0.1).toInt()
                            maxValue = (200.0 / 0.1).toInt()
                            this.value = (startingValue / 0.1).toInt()

                            setOnValueChangedListener { _, _, newVal ->
                                currentValue = newVal.toDouble()*0.1
                            }

                            (NumberPicker::class.java.getDeclaredField("mInputText")
                                .apply { isAccessible = true }
                                .get(this) as EditText).filters = emptyArray()
                        }
                    },
                )

                Spacer(modifier = Modifier.width(40.dp))

                Column {
                    Button(
                        onClick = {
                            onEvent(SummaryEvent.SavedWeightPickerValue(value = currentValue))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = stringResource(id = R.string.save))
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    OutlinedButton(
                        onClick = {
                            onEvent(SummaryEvent.DismissedWeightPickerDialog)
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = androidx.compose.ui.graphics.Color.Transparent,
                        ),
                        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary)
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