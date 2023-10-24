package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.DropdownArrow
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.PieChartWithMiddleText
import com.gmail.bogumilmecel2.fitnessappv2.common.util.TestTags
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ViewModelLayout
import com.gmail.bogumilmecel2.fitnessappv2.components.DefaultCardBackground
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SearchScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductResult
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.components.DropdownItem
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.components.RecipePriceSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.util.SelectedNutritionType
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.components.NutritionValuesList
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.shared.ProductItemDialog
import com.gmail.bogumilmecel2.ui.components.base.CustomBasicTextField
import com.gmail.bogumilmecel2.ui.components.base.CustomButton
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.components.base.WidthDivider
import com.gmail.bogumilmecel2.ui.components.base.WidthSpacer
import com.gmail.bogumilmecel2.ui.components.base.listing.ListingSwitch
import com.gmail.bogumilmecel2.ui.components.complex.ForEachDiaryList
import com.gmail.bogumilmecel2.ui.components.complex.HeaderRow
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import com.gmail.bogumilmecel2.ui.theme.LocalColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient

@Destination(navArgsDelegate = NewRecipeNavArguments::class)
@Composable
fun NewRecipeScreen(
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<SearchScreenDestination, ProductResult>
) {
    hiltViewModel<NewRecipeViewModel>().ViewModelLayout(
        navigator = navigator,
        screenTestTag = TestTags.NEW_RECIPE_SCREEN
    ) { viewModel, state ->
        val scrollState = rememberScrollState()

        resultRecipient.onNavResult { result ->
            if (result is NavResult.Value) {
                viewModel.onEvent(NewRecipeEvent.ReceivedProductResult(result.value))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            if (state.isDeleteIngredientDialogVisible && state.longClickedIngredient != null) {
                ProductItemDialog(
                    title = "${state.longClickedIngredient.productName} (${
                        stringResource(
                            id = state.longClickedIngredient.measurementUnit.getStringResWithValue(),
                            state.longClickedIngredient.weight
                        )
                    })",
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

            HeaderRow(
                middlePrimaryText = stringResource(id = R.string.add_recipe),
                onBackPressed = {
                    viewModel.onEvent(NewRecipeEvent.ClickedBackArrow)
                }
            )

            HeightSpacer(height = 10.dp)

            Column(
                modifier = Modifier.padding(horizontal = 15.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.name),
                    modifier = Modifier.padding(start = 4.dp)
                )

                HeightSpacer(height = 6.dp)

                CustomBasicTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.name,
                    placeholder = stringResource(id = R.string.recipe_name),
                    onValueChange = { viewModel.onEvent(NewRecipeEvent.EnteredName(it)) }
                )

                HeightSpacer()

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Equalizer,
                                contentDescription = null
                            )

                            WidthSpacer(width = 6.dp)

                            Text(
                                text = stringResource(id = R.string.difficulty),
                                style = MaterialTheme.typography.body2.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        HeightSpacer(height = 6.dp)

                        DropdownItem(
                            onArrowClick = { viewModel.onEvent(NewRecipeEvent.ClickedDifficultyArrow) },
                            selectedItem = state.selectedDifficulty.displayValue,
                            isDropdownExpanded = state.isDifficultyExpanded,
                            dropdownItems = state.difficulties.map { it.displayValue },
                            onItemSelected = {
                                viewModel.onEvent(NewRecipeEvent.SelectedDifficulty(state.difficulties[it]))
                            }
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = null
                            )

                            WidthSpacer(width = 6.dp)

                            Text(
                                text = stringResource(id = R.string.time),
                                style = MaterialTheme.typography.body2.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        HeightSpacer(height = 6.dp)

                        DropdownItem(
                            onArrowClick = { viewModel.onEvent(NewRecipeEvent.ClickedTimeArrow) },
                            selectedItem = state.selectedTime.getDisplayValueWithoutMin(),
                            isDropdownExpanded = state.isTimeExpanded,
                            dropdownItems = state.times.map { it.displayValue },
                            onItemSelected = { viewModel.onEvent(NewRecipeEvent.SelectedTime(state.times[it])) }
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.SupervisorAccount,
                                contentDescription = null
                            )

                            WidthSpacer(width = 6.dp)

                            Text(
                                text = stringResource(id = R.string.servings),
                                style = MaterialTheme.typography.h3
                            )
                        }

                        HeightSpacer(height = 6.dp)

                        CustomBasicTextField(
                            value = state.servings,
                            onValueChange = { viewModel.onEvent(NewRecipeEvent.EnteredServing(it)) },
                            modifier = Modifier
                                .width(96.dp)
                                .height(48.dp),
                            singleLine = true,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                HeightSpacer()

                DefaultCardBackground(modifier = Modifier.fillMaxWidth()) {
                    ListingSwitch(
                        topText = stringResource(id = R.string.new_recipe_public),
                        bottomText = stringResource(id = R.string.new_recipe_public_des),
                        checked = state.isRecipePublic,
                        onCheckedChange = { viewModel.onEvent(NewRecipeEvent.SwitchedPublic(it)) }
                    )
                }

                HeightSpacer()

                CustomButton(
                    modifier = Modifier.fillMaxWidth(),
                    leftIcon = IconVector.Save,
                    text = stringResource(id = R.string.new_recipe_save)
                ) { viewModel.onEvent(NewRecipeEvent.ClickedSaveRecipe) }
            }

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
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (state.ingredientsItemsParams.isNotEmpty()) {
                            WidthSpacer(48.dp)
                        }

                        Text(
                            text = stringResource(id = R.string.recipe_ingredients),
                            style = FitnessAppTheme.typography.HeaderSmall,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            modifier = Modifier.weight(1f)
                        )

                        if (state.ingredientsItemsParams.isNotEmpty()) {
                            DropdownArrow(
                                isArrowPointedDownwards = state.isIngredientsListExpanded,
                                onArrowClicked = { viewModel.onEvent(NewRecipeEvent.ClickedIngredientsListArrow) }
                            )
                        }
                    }

                    WidthDivider()

                    if (state.isIngredientsListExpanded) {
                        ForEachDiaryList(items = state.ingredientsItemsParams)
                    }

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.onEvent(NewRecipeEvent.ClickedAddNewIngredient) }
                            .padding(15.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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

                DefaultCardBackground(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Column(modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.recipe_information),
                                style = MaterialTheme.typography.h3,
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(id = R.string.serving).lowercase(),
                                    style = MaterialTheme.typography.body2.copy(
                                        color = FitnessAppTheme.colors.ContentSecondary
                                    )
                                )
                                Switch(
                                    checked = state.selectedNutritionType is SelectedNutritionType.Recipe,
                                    onCheckedChange = { viewModel.onEvent(NewRecipeEvent.CheckedNutritionType(it)) },
                                    colors = SwitchDefaults.colors(
                                        uncheckedThumbColor = LocalColor.Quaternary,
                                        uncheckedTrackColor = LocalColor.LightGreen3
                                    )
                                )
                                Text(
                                    text = stringResource(id = R.string.recipe).lowercase(),
                                    style = MaterialTheme.typography.body2.copy(
                                        color = FitnessAppTheme.colors.ContentSecondary
                                    )
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Max),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            PieChartWithMiddleText(
                                pieChartData = state.nutritionData.pieChartData,
                                middleText = stringResource(
                                    id = R.string.kcal_with_value,
                                    state.nutritionData.nutritionValues.calories
                                ),
                                modifier = Modifier
                                    .weight(0.4F)
                                    .fillMaxHeight()
                            )

                            NutritionValuesList(
                                modifier = Modifier
                                    .weight(0.8F)
                                    .fillMaxHeight(),
                                nutritionValues = state.nutritionData.nutritionValues
                            )
                        }
                    }
                }
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
    }
}