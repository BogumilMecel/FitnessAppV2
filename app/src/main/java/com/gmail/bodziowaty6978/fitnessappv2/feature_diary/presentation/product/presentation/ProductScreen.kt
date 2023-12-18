package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.presentation

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton.CurrentDate
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.LightRed
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.presentation.components.PriceSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.presentation.components.ProductMainSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.presentation.components.ProductTopSection
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    navArgsDelegate = ProductNavArguments::class
)
@Composable
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel()
) {
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
                backgroundColor = LightRed,
                modifier = Modifier
                    .testTag(stringResource(id = R.string.create_product))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues.calculateBottomPadding())
        ) {
            ProductTopSection(
                mealName = stringResource(id = state.mealName.getDisplayValue()),
                currentDate = CurrentDate.dateModel(LocalContext.current).valueToDisplay
                    ?: CurrentDate.dateModel(LocalContext.current).date,
                onBackArrowPressed = {
                    viewModel.onEvent(ProductEvent.ClickedBackArrow)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            ProductMainSection(
                modifier = Modifier.padding(horizontal = 15.dp),
                product = state.product,
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
                price = state.product.price,
                nutritionValues = state.product.nutritionValues,
                currency = "zł",
                unit = stringResource(id = state.product.measurementUnit.getDisplayValue()),
                priceValue = state.priceValue,
                priceFor = state.priceForValue,
                onForEntered = {
                    viewModel.onEvent(ProductEvent.EnteredPriceFor(it))
                },
                onPriceValueEntered = {
                    viewModel.onEvent(ProductEvent.EnteredPriceValue(it))
                },
                onSubmitPriceClicked = {
                    viewModel.onEvent(ProductEvent.ClickedSubmitNewPrice)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}
