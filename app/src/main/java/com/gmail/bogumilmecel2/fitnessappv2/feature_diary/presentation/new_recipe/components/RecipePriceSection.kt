package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.components.DefaultCardBackground
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.components.PriceItem
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun RecipePriceSection(
    modifier: Modifier,
    totalPrice: Double,
    servingPrice: Double,
    shouldShowPriceWarning: Boolean,
    isNewRecipe: Boolean,
    onInfoClicked: () -> Unit
) {
    DefaultCardBackground(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(id = R.string.recipe_price),
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(top = 16.dp)
                )

                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .clickable { onInfoClicked() }
                        .padding(16.dp)
                )
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )

            HeightSpacer()

            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    PriceItem(
                        priceValue = totalPrice,
                        dividerColor = MaterialTheme.colors.primary,
                        rightTextFirstLine = stringResource(id = R.string.recipe),
                        rightTextSecondLine = null
                    )

                    PriceItem(
                        priceValue = servingPrice,
                        dividerColor = MaterialTheme.colors.secondary,
                        rightTextFirstLine = if (isNewRecipe) {
                            stringResource(id = R.string.serving)
                        } else {
                            stringResource(id = R.string.selected)
                        },
                        rightTextSecondLine = null
                    )
                }

                if (shouldShowPriceWarning) {
                    HeightSpacer()

                    Text(
                        text = stringResource(id = R.string.recipe_price_warning),
                        style = MaterialTheme.typography.body2,
                        color = FitnessAppTheme.colors.Error,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}