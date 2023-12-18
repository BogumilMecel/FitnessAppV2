package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.BackArrow
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.BackHandler
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_product.components.BarcodeSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_product.components.ContainerWeightSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_product.components.NutritionSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_product.components.TextFieldSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.shared.ScannerSection

@Composable
fun NewProductScreen(
    viewModel: NewProductViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value

    BackHandler(
        enabled = state.isScannerVisible
    ) {
        if (state.isScannerVisible) {
            viewModel.onEvent(NewProductEvent.ClosedScanner)
        }
    }

    if (!state.isScannerVisible) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        if (!state.isLoading) {
                            viewModel.onEvent(NewProductEvent.ClickedSaveButton)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "Save"
                    )
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = paddingValues.calculateBottomPadding())
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    BackArrow {
                        viewModel.onEvent(NewProductEvent.ClickedBackArrow)
                    }
                }



                Spacer(modifier = Modifier.height(16.dp))

                TextFieldSection(
                    name = stringResource(id = R.string.product_name),
                    textFieldValue = state.productName,
                    onTextEntered = {
                        viewModel.onEvent(NewProductEvent.EnteredProductName(it))
                    },
                    modifier = Modifier
                        .width((LocalConfiguration.current.screenWidthDp * 0.5).dp)
                        .testTag(stringResource(id = R.string.product_name) + "TEXT_FIELD")
                )

                Spacer(modifier = Modifier.height(16.dp))


                ContainerWeightSection(
                    state = state,
                    onEvent = {
                        viewModel.onEvent(it)
                    },
                    containerWeightText = state.containerWeight,
                )

                Spacer(modifier = Modifier.height(16.dp))

                BarcodeSection(
                    barcode = state.barcode,
                    onEvent = {
                        viewModel.onEvent(it)
                    }
                )

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "Nutrition values",
                    modifier = Modifier
                        .padding(horizontal = 15.dp),
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Normal
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                NutritionSection(
                    onEvent = {
                        viewModel.onEvent(it)
                    },
                    state = state
                )
            }
        }
    } else {
        ScannerSection(
            onBarcodeScanned = { scannedBarcode ->
                scannedBarcode?.let { barcode ->
                    viewModel.onEvent(NewProductEvent.EnteredBarcode(barcode))
                } ?: viewModel.onEvent(NewProductEvent.ClosedScanner)
            }
        )
    }


}