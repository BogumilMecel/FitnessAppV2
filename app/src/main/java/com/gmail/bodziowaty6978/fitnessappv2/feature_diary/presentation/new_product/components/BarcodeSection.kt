package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_product.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.DefaultTextField
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_product.NewProductEvent

@Composable
fun BarcodeSection(
    barcode:String,
    onEvent:(NewProductEvent) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
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
            DefaultTextField(
                value = barcode,
                onValueChange = {
                    onEvent(NewProductEvent.EnteredBarcode(it))
                },
                modifier = Modifier
                    .width((LocalConfiguration.current.screenWidthDp * 0.45).dp)
                    .testTag(stringResource(id = R.string.bar_code) + "TEXT_FIELD"),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )

            IconButton(
                onClick = {
                 onEvent(NewProductEvent.ClickedScannerButton)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.barcode_scan),
                    contentDescription = stringResource(id = R.string.scan),
                    tint = MaterialTheme.colors.primary
                )

            }
        }







    }
}