package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton.CurrentDate
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.LightRed
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components.ProductNameSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components.ProductNutritionSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components.ProductTopSection


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    product: Product,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.initializeNutritionData(product)
    }

    LaunchedEffect(key1 = state){
        viewModel.errorState.collect {
            scaffoldState.snackbarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
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
                        ProductEvent.ClickedAddProduct(
                            product = product,
                        )
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
                    .testTag(stringResource(id = R.string.add_product))
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ProductTopSection(
                mealName = state.mealName,
                currentDate = CurrentDate.dateModel(LocalContext.current).valueToDisplay
                    ?: CurrentDate.dateModel(LocalContext.current).date,
                onEvent = {
                    viewModel.onEvent(it)
                }
            )

            ProductNameSection(
                currentWeight = state.weight,
                product = product,
                onEvent = {
                    viewModel.onEvent(it)
                }
            )

            ProductNutritionSection(nutritionData = state.nutritionData)
        }
    }
}
