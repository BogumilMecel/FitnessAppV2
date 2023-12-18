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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.components.CustomBasicTextField
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.NewRecipeEvent
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.NewRecipeState
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens.SearchProductItem
import com.gmail.bogumilmecel2.ui.components.base.CustomIconStyle
import com.gmail.bogumilmecel2.ui.components.base.IconParams
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
            endIconParams = IconParams(
                iconStyle = CustomIconStyle.SearchIcon,
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