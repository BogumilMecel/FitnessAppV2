package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.BackArrow
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.NewRecipeEvent
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.NewRecipeState

@Composable
fun RecipeSection(
    state: NewRecipeState,
    onEvent: (NewRecipeEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            BackArrow(
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                onEvent(NewRecipeEvent.ClickedBackArrow)
            }

            Text(
                text = stringResource(id = R.string.add_recipe),
                style = MaterialTheme.typography.h3,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        RecipeUserInputSection(
            state = state,
            onEvent = {
                onEvent(it)
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        RecipeListSection(
            state = state,
            onEvent = {
                onEvent(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
        )

        if (state.ingredients.isNotEmpty()){

            Spacer(modifier = Modifier.height(24.dp))

            RecipeNutritionSection(
                nutritionData = state.nutritionData,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                selectedNutritionType = state.selectedNutritionType,
                onSelectedNutritionType = {
                    onEvent(NewRecipeEvent.ChangedSelectedNutritionType(it))
                },
                price = state.recipePrice
            )
        }

        Spacer(modifier = Modifier.height(64.dp))
    }
}