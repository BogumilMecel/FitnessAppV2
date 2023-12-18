package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.SearchEvent
import com.gmail.bogumilmecel2.ui.components.base.ButtonParams
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.components.complex.SearchButtonParams
import com.gmail.bogumilmecel2.ui.components.complex.SearchButtonRow
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun SearchRecipeSection(
    recipes: List<Recipe>,
    onEvent: (SearchEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HeightSpacer()

        SearchButtonRow(
            buttons = listOf(
                SearchButtonParams(
                    buttonParams = ButtonParams(
                        text = stringResource(id = R.string.search_browse_recipes),
                        onClick = {

                        }
                    ),
                    icon = IconVector.Search,
                ),
                SearchButtonParams(
                    buttonParams = ButtonParams(
                        text = stringResource(id = R.string.add_recipe),
                        onClick = {
                            onEvent(SearchEvent.ClickedCreateNewRecipe)
                        }
                    ),
                    icon = IconVector.Add
                )
            ),
            buttonsColor = FitnessAppTheme.colors.Quaternary
        )

        HeightSpacer()

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            items(recipes.size) { position ->
                RecipeSection(
                    onClick = {
                        onEvent(SearchEvent.ClickedRecipe(recipes[position]))
                    },
                    recipe = recipes[position]
                )
            }
        }
    }
}