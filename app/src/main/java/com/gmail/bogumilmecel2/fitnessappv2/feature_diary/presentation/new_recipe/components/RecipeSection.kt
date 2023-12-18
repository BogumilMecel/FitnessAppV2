package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.NewRecipeEvent
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.NewRecipeState
import com.gmail.bogumilmecel2.ui.components.complex.HeaderRow

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
        HeaderRow(
            middlePrimaryText = stringResource(id = R.string.add_recipe),
            onBackPressed = {
                onEvent(NewRecipeEvent.ClickedBackArrow)
            }
        )

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