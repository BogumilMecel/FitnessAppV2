package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.TextWhite
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.round
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Price
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.ProductEvent

@Composable
fun ProductPriceSection(
    modifier: Modifier,
    price: Price?,
    nutritionValues: NutritionValues,
    unit: String,
    currency: String,
    onEvent:(ProductEvent) -> Unit,
    priceValue:String,
    priceFor:String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(if (price != null) 10 else 15),
        elevation = 3.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            var text = stringResource(id = R.string.submit_new_price)
            var spacerHeight = 4.dp
            if (price != null){
                Text(
                    text = stringResource(id = R.string.prices),
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier
                        .padding(top = 15.dp, start = 15.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                PriceRow(
                    name = stringResource(id = R.string.price_for_100_kcal),
                    value = (price.value / nutritionValues.calories.toDouble() * 100.0).round(2),
                    currency = price.currency,
                    index = 1
                )

                PriceRow(
                    name = stringResource(id = R.string.price_for_100g_of_carbohydrates),
                    value = (price.value / nutritionValues.carbohydrates * 100.0).round(2),
                    currency = price.currency,
                    index = 2
                )

                PriceRow(
                    name = stringResource(id = R.string.price_for_100g_of_protein),
                    value = (price.value / nutritionValues.protein * 100.0).round(2),
                    currency = price.currency,
                    index = 3
                )

                PriceRow(
                    name = stringResource(id = R.string.price_for_100g_of_fat),
                    value = (price.value / nutritionValues.fat * 100.0).round(2),
                    currency = price.currency,
                    index = 4
                )
            } else {
                spacerHeight = 16.dp
                text = stringResource(id = R.string.no_one_has_entered_price_for_this_product_be_the_first_one_to_do_so)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = text,
                style = MaterialTheme.typography.h3.copy(
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(spacerHeight))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp, start = 15.dp, end = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.i_paid),
                    style = MaterialTheme.typography.body1
                )

                Card(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(25)
                ) {
                    BasicTextField(
                        value = priceValue,
                        onValueChange = {
                            onEvent(ProductEvent.EnteredPriceValue(it))
                        },
                        modifier = Modifier
                            .width(80.dp)
                            .padding(horizontal = 8.dp, vertical = 10.dp)
                            .testTag(stringResource(id = R.string.WEIGHT_TEXT_FIELD)),
                        textStyle = MaterialTheme.typography.body1.copy(
                            color = TextWhite,
                            textAlign = TextAlign.End
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        cursorBrush = SolidColor(MaterialTheme.colors.primary)
                    )
                }

                Text(
                    text = String.format(stringResource(id = R.string.for_x), currency),
                    style = MaterialTheme.typography.body1
                )

                Card(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(25)
                ) {
                    BasicTextField(
                        value = priceFor,
                        onValueChange = {
                            onEvent(ProductEvent.EnteredPriceFor(it))
                        },
                        modifier = Modifier
                            .width(80.dp)
                            .padding(horizontal = 8.dp, vertical = 10.dp)
                            .testTag(stringResource(id = R.string.WEIGHT_TEXT_FIELD)),
                        textStyle = MaterialTheme.typography.body1.copy(
                            color = TextWhite,
                            textAlign = TextAlign.End
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ),
                        cursorBrush = SolidColor(MaterialTheme.colors.primary)
                    )
                }

                Text(
                    text = unit,
                    style = MaterialTheme.typography.body1
                )

                IconButton(
                    onClick = {
                        onEvent(ProductEvent.ClickedSubmitNewPrice)
                    },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.primary),
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}