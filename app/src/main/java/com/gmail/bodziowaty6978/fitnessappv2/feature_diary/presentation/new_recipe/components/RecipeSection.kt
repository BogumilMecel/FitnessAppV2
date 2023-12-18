package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.HeightSpacer
import com.gmail.bodziowaty6978.fitnessappv2.components.BackArrow
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.NewRecipeEvent
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.NewRecipeState

@Composable
fun RecipeSection(
    state: NewRecipeState,
    onEvent: (NewRecipeEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            BackArrow(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                onEvent(NewRecipeEvent.ClickedBackArrow)
            }

            Text(
                text = stringResource(id = R.string.add_recipe),
                style = MaterialTheme.typography.h2,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        HeightSpacer(height = 10.dp)

        RecipeUserInputSection(
            state = state,
            onEvent = {
                onEvent(it)
            }
        )

        HeightSpacer()

        RecipeListSection(
            state = state,
            onEvent = {
                onEvent(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        if (state.ingredients.isNotEmpty()) {

            HeightSpacer()

            RecipeNutritionSection(
                nutritionData = state.nutritionData,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                selectedNutritionType = state.selectedNutritionType,
                onSelectedNutritionType = {
                    onEvent(NewRecipeEvent.ChangedSelectedNutritionType(it))
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
}