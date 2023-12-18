package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.recipe

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.BackArrow
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.CustomBasicTextField
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.DefaultCardBackground
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.TextGrey
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.calculateNutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components.ProductNutritionSection
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search.componens.SearchProductItem

@Composable
fun RecipeScreen(
    recipe: Recipe,
    mealName: String,
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val scrollState = rememberScrollState()
    val dropdownArrowIngredientsListState by animateFloatAsState(
        targetValue = if (state.isIngredientsListExpanded) 180f else 0f
    )

    LaunchedEffect(key1 = true) {
        viewModel.initializeRecipe(recipe)
    }

    var fabHeight by remember {
        mutableStateOf(0)
    }

    Scaffold(
//        floatingActionButton = {
//            ExtendedFloatingActionButton(
//                text = {
//                    Text(text = "Save To $mealName")
//                },
//                icon = {
//                    Icon(imageVector = Icons.Default.Save, contentDescription = null)
//                },
//                onClick = {
//
//                },
//                modifier = Modifier.onGloballyPositioned {
//                    fabHeight = it.size.height
//                }
//            )
//        }
    ) {
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
                        text = "Spaghetti Bolognese With Tagiatelle Pasta",
                        style = MaterialTheme.typography.h3.copy(
                            textAlign = TextAlign.Center
                        ),
                        maxLines = 4
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = state.recipe.nutritionValues.calories.toString() + " kcal",
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


            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

            }

            Spacer(modifier = Modifier.height(24.dp))

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
                            modifier = Modifier.padding(15.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
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
                                    text = "${recipe.timeNeeded} min",
                                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Medium)
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Time to make",
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
                            modifier = Modifier.padding(15.dp),
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
                                    text = "${state.recipe.difficulty} / 5",
                                    style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Medium)
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "Difficulty",
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
                                        text = "Portions",
                                        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Medium),
                                    )
                                }


                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "Recipe serves ${state.recipe.servings} people",
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

                        Button(onClick = { /*TODO*/ },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Default.Save, contentDescription = null)

                                Spacer(modifier = Modifier.width(12.dp))

                                Text(
                                    text = "Save to $mealName",
                                    style = MaterialTheme.typography.button
                                )
                            }
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
                                text = "Ingredients",
                                style = MaterialTheme.typography.body1.copy(
                                    fontWeight = FontWeight.Medium
                                ),
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )

                            IconButton(
                                onClick = {
                                    viewModel.onEvent(RecipeEvent.ClickedExpandIngredientsList)
                                },
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .rotate(
                                        degrees = dropdownArrowIngredientsListState
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null
                                )
                            }

                        }

                        if (state.isIngredientsListExpanded) {

                            Spacer(modifier = Modifier.height(5.dp))

                            Divider()

                            val ingredients = state.recipe.ingredients

                            ingredients.forEach { ingredient ->
                                SearchProductItem(
                                    weight = ingredient.weight,
                                    unit = ingredient.product.unit,
                                    name = ingredient.product.name,
                                    clickable = false,
                                    calories = ingredient.product.calculateNutritionValues(
                                        ingredient.weight
                                    ).calories,
                                    onItemClick = {

                                    },
                                    background = Color.Transparent
                                )
                            }
                        }
                    }
                }
            )

            if (state.isUserRecipeOwner) {
                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(horizontal = 15.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.secondaryVariant
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = null)

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "Edit recipe",
                            style = MaterialTheme.typography.button
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height((fabHeight - 48).dp))
        }
    }
}