package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ConfigureViewModel
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.components.PriceSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.components.ProductMainSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.components.SubmitNewPriceDialog
import com.gmail.bogumilmecel2.ui.components.complex.HeaderRow
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(navArgsDelegate = ProductNavArguments::class)
@Composable
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    viewModel.ConfigureViewModel(navigator)
    val state = viewModel.state.collectAsStateWithLifecycle().value

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(
                        text = stringResource(id = R.string.save).uppercase(),
                        color = Color.Black,
                        style = MaterialTheme.typography.button
                    )
                },
                onClick = {
                    viewModel.onEvent(
                        ProductEvent.ClickedAddProduct
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                },
                backgroundColor = FitnessAppTheme.colors.Error,
                modifier = Modifier
                    .testTag(stringResource(id = R.string.create_product))
            )
        }
    ) { paddingValues ->
        if (state.isSubmitPriceDialogVisible) {
            SubmitNewPriceDialog(
                productName = state.productName,
                price = state.priceValue,
                forValue = state.priceForValue,
                currency = Currency.PLN,
                measurementUnit = state.productMeasurementUnit,
                onEvent = {
                    viewModel.onEvent(it)
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues.calculateBottomPadding())
        ) {
            HeaderRow(
                middlePrimaryText = stringResource(id = state.mealName.getDisplayValue()),
                middleSecondaryText = state.date,
                onBackPressed = {
                    viewModel.onEvent(ProductEvent.ClickedBackArrow)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            ProductMainSection(
                modifier = Modifier.padding(horizontal = 15.dp),
                productName = state.productName,
                currentWeight = state.weight,
                onWeightEntered = {
                    viewModel.onEvent(ProductEvent.EnteredWeight(it))
                },
                nutritionData = state.nutritionData
            )

            Spacer(modifier = Modifier.height(10.dp))

            PriceSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                productPrice = state.productPrice,
                onSubmitPriceClicked = {
                    viewModel.onEvent(ProductEvent.ClickedSubmitNewPrice)
                },
                onInfoButtonClicked = {
                    viewModel.onEvent(ProductEvent.ClickedInfoPriceButton)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
