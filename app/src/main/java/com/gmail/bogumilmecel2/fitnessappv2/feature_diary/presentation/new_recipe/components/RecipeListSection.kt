package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.DropdownArrow
import com.gmail.bogumilmecel2.fitnessappv2.components.DefaultCardBackground
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.NewRecipeEvent
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.NewRecipeState
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens.SearchProductItem

@Composable
fun RecipeListSection(
    modifier: Modifier, state: NewRecipeState, onEvent: (NewRecipeEvent) -> Unit
) {
    DefaultCardBackground(
        modifier = modifier
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
                        onEvent(NewRecipeEvent.ClickedIngredientsListArrow)
                    }
                )
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )

            if (state.isIngredientsListExpanded) {
                state.ingredients.forEach { ingredient ->
                    SearchProductItem(
                        name = ingredient.productName,
                        unit = stringResource(id = ingredient.measurementUnit.getStringRes()),
                        calories = ingredient.nutritionValues.calories,
                        weight = ingredient.weight,
                        onItemLongClick = {
                            onEvent(NewRecipeEvent.LongClickedIngredient(ingredient = ingredient))
                        }
                    )
                }
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onEvent(NewRecipeEvent.ClickedAddNewIngredient)
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
}