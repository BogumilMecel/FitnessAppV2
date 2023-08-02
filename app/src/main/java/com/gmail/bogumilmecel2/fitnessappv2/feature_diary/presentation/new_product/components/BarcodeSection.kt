package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_product.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_product.NewProductEvent
import com.gmail.bogumilmecel2.ui.components.base.CustomBasicTextField
import com.gmail.bogumilmecel2.ui.components.base.CustomIcon
import com.gmail.bogumilmecel2.ui.components.base.IconVector

@Composable
fun BarcodeSection(
    barcode: String,
    onEvent: (NewProductEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = stringResource(id = R.string.bar_code),
            style = MaterialTheme.typography.body1
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomBasicTextField(
                value = barcode,
                onValueChange = {
                    onEvent(NewProductEvent.EnteredBarcode(it))
                },
                modifier = Modifier
                    .width((LocalConfiguration.current.screenWidthDp * 0.4).dp)
                    .testTag(stringResource(id = R.string.bar_code) + "TEXT_FIELD"),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true
            )

            IconButton(
                onClick = {
                    onEvent(NewProductEvent.ClickedScannerButton)
                }
            ) {
                CustomIcon(icon = IconVector.barcode())
            }
        }
    }
}