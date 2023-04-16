package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.recipe

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.CustomButton
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.DropdownArrow
import com.gmail.bogumilmecel2.fitnessappv2.components.CustomBasicTextField
import com.gmail.bogumilmecel2.fitnessappv2.components.DefaultCardBackground
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.components.RecipePriceSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.components.ProductNutritionSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens.SearchProductItem
import com.gmail.bogumilmecel2.ui.components.base.CustomIconStyle
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.IconParams
import com.gmail.bogumilmecel2.ui.components.base.WidthSpacer
import com.gmail.bogumilmecel2.ui.components.complex.HeaderRow
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import com.ramcosta.composedestinations.annotation.Destination

@Destination(
    navArgsDelegate = RecipeNavArguments::class
)
@Composable
fun RecipeScreen(
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        HeaderRow(
            middlePrimaryText = state.recipe.name,
            middleSecondaryText = stringResource(
                id = R.string.kcal_with_value,
                state.recipe.nutritionValues.calories
            ),
            onBackPressed = {
                viewModel.onEvent(RecipeEvent.ClickedBackArrow)
            },
            endIconParams = IconParams(
                iconStyle = if (!state.isFavorite) {
                    CustomIconStyle.Heart
                } else {
                    CustomIconStyle.HeartFilled
                },
                onClick = {
                    viewModel.onEvent(RecipeEvent.ClickedFavorite)
                }
            )
        )

        HeightSpacer()

        Row(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DefaultCardBackground(
                modifier = Modifier.weight(1F),
                content = {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccessTime,
                                contentDescription = null
                            )

                            WidthSpacer(width = 8.dp)

                            Text(
                                text = state.recipe.timeRequired.displayValue,
                                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Medium)
                            )
                        }

                        HeightSpacer(height = 4.dp)

                        Text(
                            text = stringResource(id = R.string.recipe_time_required),
                            style = MaterialTheme.typography.body2.copy(color = FitnessAppTheme.colors.ContentSecondary)
                        )
                    }
                }
            )

            WidthSpacer(width = 12.dp)

            DefaultCardBackground(
                modifier = Modifier.weight(1F),
                content = {
                    Column(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Equalizer,
                                contentDescription = null
                            )

                            HeightSpacer(height = 8.dp)

                            Text(
                                text = "${state.recipe.difficulty.displayValue} / 5",
                                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Medium)
                            )
                        }

                        HeightSpacer(height = 4.dp)

                        Text(
                            text = stringResource(id = R.string.recipe_difficulty),
                            style = MaterialTheme.typography.body2.copy(color = FitnessAppTheme.colors.ContentSecondary)
                        )
                    }
                }
            )
        }

        HeightSpacer()

        DefaultCardBackground(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(0.75F)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SupervisorAccount,
                                    contentDescription = null
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Text(
                                    text = stringResource(id = R.string.servings),
                                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Medium),
                                )
                            }

                            HeightSpacer(height = 4.dp)

                            Text(
                                text = stringResource(
                                    id = R.string.recipe_serves,
                                    state.recipe.servings
                                ),
                                style = MaterialTheme.typography.body2.copy(color = FitnessAppTheme.colors.ContentSecondary)
                            )
                        }


                        CustomBasicTextField(
                            value = state.servings,
                            onValueChange = {
                                viewModel.onEvent(RecipeEvent.EnteredServings(it))
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier.weight(0.25F)
                        )
                    }

                    HeightSpacer(height = 12.dp)

                    CustomButton(
                        modifier = Modifier.fillMaxWidth(),
                        iconLeft = Icons.Default.Save,
                        text = stringResource(
                            id = R.string.recipe_save_to,
                            state.mealName
                        )
                    ) {
                        viewModel.onEvent(RecipeEvent.ClickedSaveRecipeDiaryEntry)
                    }
                }
            }
        )

        HeightSpacer()

        ProductNutritionSection(
            nutritionData = state.nutritionData,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 13.dp)
        )

        if (state.recipePrice != null && state.servingPrice != null) {
            HeightSpacer()

            RecipePriceSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                totalPrice = state.recipePrice.totalPrice,
                servingPrice = state.servingPrice,
                shouldShowPriceWarning = state.recipePrice.shouldShowPriceWarning,
                isNewRecipe = false,
                onInfoClicked = {

                }
            )
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
            content = {
                Column(
                    modifier = Modifier.padding(vertical = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {

                        Text(
                            text = stringResource(id = R.string.recipe_ingredients),
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier
                                .align(Alignment.Center)
                        )

                        DropdownArrow(
                            isArrowPointedDownwards = state.isIngredientsListExpanded,
                            modifier = Modifier.align(Alignment.CenterEnd),
                            onArrowClicked = {
                                viewModel.onEvent(RecipeEvent.ClickedExpandIngredientsList)
                            }
                        )
                    }

                    if (state.isIngredientsListExpanded) {

                        Spacer(modifier = Modifier.height(5.dp))

                        Divider()

                        val ingredients = state.recipe.ingredients

                        ingredients.forEach { ingredient ->
                            SearchProductItem(
                                weight = ingredient.weight,
                                unit = stringResource(id = ingredient.measurementUnit.getDisplayValue()),
                                name = ingredient.productName,
                                calories = ingredient.nutritionValues.calories,
                                background = Color.Transparent
                            )
                        }
                    }
                }
            }
        )

        if (state.isUserRecipeOwner) {
            Spacer(modifier = Modifier.height(12.dp))

            CustomButton(
                modifier = Modifier.padding(horizontal = 15.dp),
                iconLeft = Icons.Default.Edit,
                text = stringResource(id = R.string.recipe_edit),
                backgroundColor = MaterialTheme.colors.secondaryVariant
            ) {

            }
        }
    }
}