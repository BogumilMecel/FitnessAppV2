package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.BackHandler
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ConfigureViewModel
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_product.components.BarcodeSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_product.components.ContainerWeightSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_product.components.NutritionSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_product.components.TextFieldSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.shared.ScannerSection
import com.gmail.bogumilmecel2.ui.components.complex.HeaderRow
import com.ramcosta.composedestinations.annotation.Destination

@Destination(navArgsDelegate = NewProductNavArguments::class)
@Composable
fun NewProductScreen(viewModel: NewProductViewModel = hiltViewModel()) {
    val state = viewModel.state.collectAsState().value

    viewModel.ConfigureViewModel()

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
                HeaderRow(
                    middlePrimaryText = stringResource(id = R.string.create_product),
                    onBackPressed = {
                        viewModel.onEvent(NewProductEvent.ClickedBackArrow)
                    }
                )

                Spacer(modifier = Modifier.height(5.dp))

                TextFieldSection(
                    name = stringResource(id = R.string.product_name),
                    textFieldValue = state.productName,
                    onTextEntered = {
                        viewModel.onEvent(NewProductEvent.EnteredProductName(it))
                    },
                    modifier = Modifier
                        .weight(0.4F)
                        .testTag(stringResource(id = R.string.product_name) + "TEXT_FIELD")
                )

                Spacer(modifier = Modifier.height(12.dp))


                ContainerWeightSection(
                    state = state,
                    onEvent = {
                        viewModel.onEvent(it)
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))

                BarcodeSection(
                    barcode = state.barcode,
                    onEvent = {
                        viewModel.onEvent(it)
                    }
                )

                Spacer(modifier = Modifier.height(36.dp))

                Text(
                    text = stringResource(id = R.string.nutrition_values),
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