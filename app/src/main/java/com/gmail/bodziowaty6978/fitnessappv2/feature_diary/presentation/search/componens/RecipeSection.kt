package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search.componens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.TextGrey
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Recipe

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecipeSection(
    onClick: () -> Unit,
    recipe: Recipe,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(15),
        elevation = 6.dp,
        onClick = {
            onClick()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                recipe.imageUrl?.let { url ->
                    Image(
                        painter = rememberAsyncImagePainter(url),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = recipe.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "${recipe.nutritionValues.calories} kcal",
                    style = MaterialTheme.typography.body2.copy(
                        color = TextGrey
                    )
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "${recipe.timeRequired.displayValue} min",
                    style = MaterialTheme.typography.body2.copy(
                        color = TextGrey
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}