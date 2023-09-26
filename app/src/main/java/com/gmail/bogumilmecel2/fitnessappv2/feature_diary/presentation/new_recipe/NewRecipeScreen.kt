package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.DropdownArrow
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ConfigureViewModel
import com.gmail.bogumilmecel2.fitnessappv2.components.DefaultCardBackground
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.components.RecipeNutritionSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.components.RecipePriceSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.components.RecipeUserInputSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.components.ProductMainSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.shared.ProductItemDialog
import com.gmail.bogumilmecel2.ui.components.base.CustomBasicTextField
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.IconButtonParams
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.components.complex.ForEachSearchList
import com.gmail.bogumilmecel2.ui.components.complex.HeaderRow
import com.gmail.bogumilmecel2.ui.components.complex.SearchList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(navArgsDelegate = NewRecipeNavArguments::class)
@Composable
fun NewRecipeScreen(
    viewModel: NewRecipeViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    viewModel.ConfigureViewModel(navigator = navigator)
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
                Column(modifier = Modifier.fillMaxSize()) {
                    HeaderRow(
                        middlePrimaryText = "Add ingredient to a recipe",
                        onBackPressed = {
                            viewModel.onEvent(NewRecipeEvent.ClickedBackArrow)
                        },
                        endIconButtonParams = IconButtonParams(
                            iconVector = IconVector.Search,
                            onClick = {
                                viewModel.onEvent(NewRecipeEvent.ClickedSearchButton)
                            }
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    CustomBasicTextField(
                        value = state.searchText,
                        onValueChange = {
                            viewModel.onEvent(NewRecipeEvent.EnteredSearchText(it))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp),
                        placeholder = stringResource(id = R.string.product_name)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    SearchList(items = state.searchItems)
                }
            } else if (state.isRecipeSectionVisible) {

                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    if (state.isDeleteIngredientDialogVisible) {
                        state.longClickedIngredient?.let {
                            ProductItemDialog(
                                title = "${it.productName} (${stringResource(id = it.measurementUnit.getStringResWithValue(), it.weight)})",
                                onDeleteButtonClicked = {
                                    viewModel.onEvent(NewRecipeEvent.ClickedDeleteInIngredientDialog)
                                },
                                onEditButtonClicked = {

                                },
                                onDismissRequest = {
                                    viewModel.onEvent(NewRecipeEvent.DismissedDeleteIngredientDialog)
                                }
                            )
                        }
                    }

                    HeaderRow(
                        middlePrimaryText = stringResource(id = R.string.add_recipe),
                        onBackPressed = {
                            viewModel.onEvent(NewRecipeEvent.ClickedBackArrow)
                        }
                    )

                    HeightSpacer(height = 10.dp)

                    RecipeUserInputSection(
                        state = state,
                        onEvent = {
                            viewModel.onEvent(it)
                        }
                    )

                    HeightSpacer()

                    DefaultCardBackground(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .animateContentSize(
                                animationSpec = tween(
                                    durationMillis = 400,
                                    easing = LinearOutSlowInEasing
                                )
                            ),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = stringResource(id = R.string.recipe_ingredients),
                                    style = MaterialTheme.typography.h3.copy(
                                        textAlign = TextAlign.Center
                                    ),
                                    modifier = Modifier.align(Alignment.Center)
                                )

                                DropdownArrow(
                                    isArrowPointedDownwards = state.isIngredientsListExpanded,
                                    modifier = Modifier.align(Alignment.CenterEnd),
                                    onArrowClicked = {
                                        viewModel.onEvent(NewRecipeEvent.ClickedIngredientsListArrow)
                                    }
                                )
                            }

                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                            )

                            if (state.isIngredientsListExpanded) {
                                ForEachSearchList(items = state.ingredientsItemsParams)
                            }

                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                            )

                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.onEvent(NewRecipeEvent.ClickedAddNewIngredient)
                                }
                                .padding(15.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.primary
                                )

                                Spacer(modifier = Modifier.width(6.dp))

                                Text(
                                    text = "Add ingredient",
                                    style = MaterialTheme.typography.button.copy(
                                        color = MaterialTheme.colors.primary
                                    )
                                )
                            }
                        }
                    }

                    if (state.ingredientsItemsParams.isNotEmpty()) {

                        HeightSpacer()

                        RecipeNutritionSection(
                            nutritionData = state.nutritionData,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            selectedNutritionType = state.selectedNutritionType,
                            onSelectedNutritionType = {
                                viewModel.onEvent(NewRecipeEvent.ChangedSelectedNutritionType(it))
                            },
                        )
                    }

                    if (state.recipePrice != null && state.servingPrice != null) {
                        HeightSpacer()

                        RecipePriceSection(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            totalPrice = state.recipePrice.totalPrice,
                            servingPrice = state.servingPrice,
                            shouldShowPriceWarning = state.recipePrice.shouldShowPriceWarning,
                            isNewRecipe = true,
                            onInfoClicked = {

                            }
                        )
                    }

                    HeightSpacer(height = 64.dp)
                }
            } else if (state.isProductSectionVisible) {
                state.selectedProduct?.let { product ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        HeaderRow(
                            middlePrimaryText = stringResource(id = R.string.add_product),
                            onBackPressed = {
                                viewModel.onEvent(NewRecipeEvent.ClickedBackArrow)
                            }
                        )

                        HeightSpacer()

                        ProductMainSection(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            productName = product.name,
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