package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.ui.components.base.ButtonParams
import com.gmail.bogumilmecel2.ui.components.base.CustomButton
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.components.complex.SearchButtonParams
import com.gmail.bogumilmecel2.ui.components.complex.SearchButtonRow
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun SearchEverythingSection(
    searchItems: List<SearchItemParams>,
    isBarcodeLayoutVisible: Boolean,
    onScanBarcodeClicked: () -> Unit,
    onBarcodeAddProductClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        SearchButtonRow(
            buttons = listOf(
                SearchButtonParams(
                    buttonParams = ButtonParams(
                        text = stringResource(id = R.string.search_quick_add),
                        onClick = {

                        }
                    ),
                    icon = IconVector.Add
                ),
                SearchButtonParams(
                    buttonParams = ButtonParams(
                        text = stringResource(id = R.string.scan_barcode),
                        onClick = {
                            onScanBarcodeClicked()
                        }
                    ),
                    icon = IconVector.barcode(),
                )
            ),
            buttonsColor = FitnessAppTheme.colors.Secondary
        )

        HeightSpacer()

        Box {
            if (isBarcodeLayoutVisible) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.there_is_no_product_with_provided_barcode_do_you_want_to_add_it),
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = FitnessAppTheme.typography.HeaderLarge,
                        textAlign = TextAlign.Center
                    )

                    HeightSpacer()

                    CustomButton(
                        onClick = onBarcodeAddProductClicked,
                        text = stringResource(id = R.string.add)
                    )
                }
            } else if (searchItems.isNotEmpty()) {
                SearchList(searchItems)
            }
        }
    }
}