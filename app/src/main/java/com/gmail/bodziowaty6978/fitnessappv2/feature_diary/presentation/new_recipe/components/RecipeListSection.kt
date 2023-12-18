package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.calculateNutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.NewRecipeEvent
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.NewRecipeState
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search.componens.SearchProductItem

@Composable
fun RecipeListSection(
    modifier: Modifier,
    state: NewRecipeState,
    onEvent: (NewRecipeEvent) -> Unit
) {
    Card(
        modifier = modifier,
        elevation = 3.dp,
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Ingredients List",
                style = MaterialTheme.typography.h3.copy(
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )

            state.ingredients.forEach { ingredient ->
                val product = ingredient.product
                SearchProductItem(
                    name = product.name,
                    unit = product.unit,
                    calories = product.calculateNutritionValues(ingredient.weight).calories,
                    weight = ingredient.weight
                ) {

                }
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .clickable {
                        onEvent(NewRecipeEvent.ClickedAddNewIngredient)
                    },
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
}