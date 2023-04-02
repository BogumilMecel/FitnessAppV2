package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.components.BackArrow
import com.gmail.bodziowaty6978.fitnessappv2.components.CustomBasicTextField
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.NewRecipeEvent
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.NewRecipeState
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search.componens.SearchProductItem

@Composable
fun SearchForProductSection(
    onEvent: (NewRecipeEvent) -> Unit,
    state: NewRecipeState
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        ) {
            BackArrow(modifier = Modifier.align(Alignment.CenterStart)) {
                onEvent(NewRecipeEvent.ClickedBackArrow)
            }

            Text(
                text = "Add ingredient to a recipe",
                style = MaterialTheme.typography.h3,
                modifier = Modifier.align(Alignment.Center)
            )

            IconButton(
                onClick = {
                    onEvent(NewRecipeEvent.ClickedSearchButton)
                },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.padding(5.dp),
                    tint = MaterialTheme.colors.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        CustomBasicTextField(
            value = state.searchText,
            onValueChange = {
                onEvent(NewRecipeEvent.EnteredSearchText(it))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            placeholder = "Product name"
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(state.searchItems.size) {
                val product = state.searchItems[it]
                SearchProductItem(
                    name = product.name,
                    unit = stringResource(id = product.measurementUnit.getDisplayValue()),
                    weight = 100,
                    calories = product.nutritionValues.calories,
                    onItemClick = {
                        onEvent(NewRecipeEvent.ClickedProduct(state.searchItems[it]))
                    }
                )
            }
        }
    }

}