package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search.componens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.Beige1
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search.SearchEvent

@Composable
fun SearchRecipeSection(
    recipe: List<Recipe>,
    onEvent: (SearchEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
        ) {
            SearchButton(
                text = stringResource(id = R.string.filter),
                color = Beige1,
                onClick = {

                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.filter),
                        contentDescription = stringResource(id = R.string.filter),
                        tint = Color.Black
                    )
                },
                modifier = Modifier
                    .weight(1F)
                    .testTag(stringResource(id = R.string.scan))
            )

            SearchButton(
                text = stringResource(id = R.string.add_recipe),
                modifier = Modifier.weight(1F),
                color = MaterialTheme.colors.secondary,
                onClick = {
                    onEvent(SearchEvent.ClickedCreateNewRecipe)
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add),
                        tint = Color.Black
                    )
                },
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(recipe.size) { position ->
                RecipeSection(
                    onClick = {
                        /*TODO*/
                    },
                    recipe = recipe[position]
                )
            }
        }
    }
}