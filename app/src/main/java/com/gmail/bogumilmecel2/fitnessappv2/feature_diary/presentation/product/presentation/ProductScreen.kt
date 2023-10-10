package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ViewModelLayout
import com.gmail.bogumilmecel2.fitnessappv2.components.DefaultCardBackground
import com.gmail.bogumilmecel2.fitnessappv2.components.defaultRoundedCornerShapeValue
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductResult
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.components.PriceItem
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.components.ProductMainSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.components.SubmitNewPriceDialog
import com.gmail.bogumilmecel2.ui.components.base.CustomIcon
import com.gmail.bogumilmecel2.ui.components.base.CustomIconButton
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.IconButtonParams
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.components.base.WidthDivider
import com.gmail.bogumilmecel2.ui.components.base.WidthSpacer
import com.gmail.bogumilmecel2.ui.components.complex.HeaderRow
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import com.gmail.bogumilmecel2.ui.theme.LocalColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Destination(navArgsDelegate = ProductNavArguments::class)
@Composable
fun ProductScreen(
    navigator: DestinationsNavigator,
    resultBackNavigator: ResultBackNavigator<ProductResult>
) {
    hiltViewModel<ProductViewModel>().ViewModelLayout(
        navigator = navigator,
        resultBackNavigator = resultBackNavigator
    ) { viewModel, state ->
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
                    middlePrimaryText = state.headerPrimaryText,
                    middleSecondaryText = state.headerSecondaryText,
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

                if (state.productPrice != null || state.submitPriceButtonVisible) {
                    DefaultCardBackground(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = stringResource(id = R.string.prices),
                                    style = FitnessAppTheme.typography.HeaderSmall,
                                    textAlign = TextAlign.Center,
                                    color = FitnessAppTheme.colors.ContentPrimary,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .align(Alignment.Center)
                                )

                                CustomIconButton(
                                    params = IconButtonParams(
                                        iconVector = IconVector.Info,
                                        onClick = { viewModel.onEvent(ProductEvent.ClickedInfoPriceButton) }
                                    ),
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }

                            state.productPrice?.let {
                                WidthDivider()

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp),
                                ) {
                                    Column(
                                        modifier = Modifier.weight(1f),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        PriceItem(
                                            priceValue = it.valueFor100Calories,
                                            dividerColor = FitnessAppTheme.colors.Primary,
                                            rightTextFirstLine = "100",
                                            rightTextSecondLine = stringResource(id = R.string.calories)
                                        )

                                        HeightSpacer()

                                        PriceItem(
                                            priceValue = it.valueFor100Carbohydrates,
                                            dividerColor = LocalColor.LightGreen3,
                                            rightTextFirstLine = stringResource(
                                                id = R.string.measurement_unit_gram_with_value,
                                                100
                                            ),
                                            rightTextSecondLine = stringResource(id = R.string.carbs)
                                        )
                                    }

                                    Column(
                                        modifier = Modifier.weight(1f),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        PriceItem(
                                            priceValue = it.valueFor10Protein,
                                            dividerColor = LocalColor.BlueViolet3,
                                            rightTextFirstLine = stringResource(
                                                id = R.string.measurement_unit_gram_with_value,
                                                10
                                            ),
                                            rightTextSecondLine = stringResource(id = R.string.protein)
                                        )

                                        HeightSpacer()

                                        PriceItem(
                                            priceValue = it.valueFor10Fat,
                                            dividerColor = LocalColor.OrangeYellow3,
                                            rightTextFirstLine = stringResource(
                                                id = R.string.measurement_unit_gram_with_value,
                                                10
                                            ),
                                            rightTextSecondLine = stringResource(id = R.string.fat)
                                        )
                                    }
                                }
                            } ?: kotlin.run {
                                Text(
                                    text = stringResource(id = R.string.product_no_price),
                                    style = FitnessAppTheme.typography.ParagraphMedium,
                                    color = FitnessAppTheme.colors.ContentSecondary,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 16.dp)
                                )
                            }

                            if (state.submitPriceButtonVisible) {
                                WidthDivider()

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(
                                            RoundedCornerShape(
                                                bottomEnd = defaultRoundedCornerShapeValue(),
                                                bottomStart = defaultRoundedCornerShapeValue(),
                                                topStart = 0.dp,
                                                topEnd = 0.dp
                                            )
                                        )
                                        .clickable { viewModel.onEvent(ProductEvent.ClickedSubmitNewPrice) }
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    CustomIcon(icon = IconVector.Add)

                                    WidthSpacer(width = 8.dp)

                                    Text(
                                        text = stringResource(id = R.string.submit_new_price),
                                        style = FitnessAppTheme.typography.Button,
                                        color = FitnessAppTheme.colors.Primary
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
