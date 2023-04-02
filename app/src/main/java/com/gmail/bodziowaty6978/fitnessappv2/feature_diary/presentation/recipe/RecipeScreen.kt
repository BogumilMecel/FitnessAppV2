package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.recipe

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
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.BackArrow
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.CustomBasicTextField
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.CustomButton
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.DefaultCardBackground
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.DropdownArrow
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.HeightSpacer
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.TextGrey
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.presentation.components.ProductNutritionSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search.componens.SearchProductItem
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackArrow(
                modifier = Modifier.weight(0.1F)
            ) {
                viewModel.onEvent(RecipeEvent.ClickedBackArrow)
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(0.8F)
                    .padding(top = 10.dp)
            ) {
                Text(
                    text = state.recipe.name,
                    style = MaterialTheme.typography.h3.copy(
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = R.string.kcal_with_value, state.recipe.nutritionValues.calories),
                    style = MaterialTheme.typography.body2.copy(
                        color = TextGrey
                    )
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            IconButton(
                onClick = {
                    viewModel.onEvent(RecipeEvent.ClickedFavorite)
                },
                modifier = Modifier.weight(0.1F)
            ) {
                Icon(
                    imageVector = if (!state.isFavorite) {
                        Icons.Default.FavoriteBorder
                    } else {
                        Icons.Filled.Favorite
                    },
                    contentDescription = null
                )
            }
        }

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
                            .padding(15.dp)
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

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = state.recipe.timeRequired.displayValue,
                                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Medium)
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = stringResource(id = R.string.recipe_time_required),
                            style = MaterialTheme.typography.body2.copy(color = TextGrey)
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.width(12.dp))

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

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "${state.recipe.difficulty.displayValue} / 5",
                                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Medium)
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = stringResource(id = R.string.recipe_difficulty),
                            style = MaterialTheme.typography.body2.copy(color = TextGrey)
                        )
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        DefaultCardBackground(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 13.dp),
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
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


                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = stringResource(id = R.string.recipe_serves, state.recipe.servings),
                                style = MaterialTheme.typography.body2.copy(color = TextGrey)
                            )
                        }


                        CustomBasicTextField(
                            value = state.portions,
                            onValueChange = {
                                viewModel.onEvent(RecipeEvent.EnteredPortions(it))
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            modifier = Modifier.weight(0.25F)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    CustomButton(
                        modifier = Modifier.fillMaxWidth(),
                        iconLeft = Icons.Default.Save,
                        text = stringResource(id = R.string.recipe_save_to, state.mealName)
                    ) {
                        viewModel.onEvent(RecipeEvent.ClickedSaveRecipeDiaryEntry)
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        ProductNutritionSection(
            nutritionData = state.nutritionData,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 13.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        DefaultCardBackground(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
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
                            .padding(horizontal = 15.dp)
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