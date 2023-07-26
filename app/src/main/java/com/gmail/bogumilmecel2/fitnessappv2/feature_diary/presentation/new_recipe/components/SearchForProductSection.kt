package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.NewRecipeEvent
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.NewRecipeState
import com.gmail.bogumilmecel2.ui.components.base.CustomBasicTextField
import com.gmail.bogumilmecel2.ui.components.base.IconButtonParams
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.components.complex.HeaderRow

@Composable
fun SearchForProductSection(
    onEvent: (NewRecipeEvent) -> Unit,
    state: NewRecipeState
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderRow(
            middlePrimaryText = "Add ingredient to a recipe",
            onBackPressed = {
                onEvent(NewRecipeEvent.ClickedBackArrow)
            },
            endIconButtonParams = IconButtonParams(
                iconVector = IconVector.Search,
                onClick = {
                    onEvent(NewRecipeEvent.ClickedSearchButton)
                }
            )
        )

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
//                SearchItem(
//                    name = product.name,
//                    unit = stringResource(id = product.measurementUnit.getStringRes()),
//                    weight = 100,
//                    calories = product.nutritionValues.calories,
//                    onItemClick = {
//                        onEvent(NewRecipeEvent.ClickedProduct(state.searchItems[it]))
//                    }
//                )
            }
        }
    }

}