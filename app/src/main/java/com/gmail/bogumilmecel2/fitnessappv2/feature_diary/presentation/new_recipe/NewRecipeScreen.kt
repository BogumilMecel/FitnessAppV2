package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe

import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.components.BackArrow
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.components.RecipeSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.components.SearchForProductSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.components.ProductMainSection
import com.ramcosta.composedestinations.annotation.Destination

@Destination(navArgsDelegate = NewRecipeNavArguments::class)
@Composable
fun NewRecipeScreen(
    viewModel: NewRecipeViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    Scaffold(
        floatingActionButton = {
            if (state.isProductSectionVisible) {
                ExtendedFloatingActionButton(
                    onClick = { viewModel.onEvent(NewRecipeEvent.ClickedSaveProduct) },
                    text = {
                        Text(
                            text = "SAVE PRODUCT",
                            style = MaterialTheme.typography.button
                        )
                    },
                    icon = {
                        Icon(imageVector = Icons.Default.Save, contentDescription = null)
                    }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues.calculateBottomPadding())) {
            if (state.isSearchSectionVisible) {
                SearchForProductSection(
                    onEvent = {
                        viewModel.onEvent(it)
                    },
                    state = state
                )
            } else if (state.isRecipeSectionVisible) {
                RecipeSection(
                    state = state,
                    onEvent = {
                        viewModel.onEvent(it)
                    }
                )
            } else if (state.isProductSectionVisible) {
                state.selectedProduct?.let { product ->
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            BackArrow(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                            ) {
                                viewModel.onEvent(NewRecipeEvent.ClickedBackArrow)
                            }

                            Text(
                                text = stringResource(id = R.string.add_product),
                                style = MaterialTheme.typography.h3,
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        ProductMainSection(
                            product = product,
                            currentWeight = state.productWeight,
                            onWeightEntered = {
                                viewModel.onEvent(NewRecipeEvent.EnteredProductWeight(it))
                            },
                            nutritionData = state.nutritionData
                        )
                    }
                }
            }
        }
    }
}