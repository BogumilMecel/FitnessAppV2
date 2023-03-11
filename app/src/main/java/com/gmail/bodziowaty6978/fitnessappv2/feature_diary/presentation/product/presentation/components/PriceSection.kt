package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.CustomBasicTextField
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.DefaultCardBackground
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.TextWhite
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Price

@Composable
fun PriceSection(
    modifier: Modifier,
    price: Price?,
    nutritionValues: NutritionValues,
    unit: String,
    currency: String,
    priceValue: String,
    priceFor: String,
    onPriceValueEntered: (String) -> Unit,
    onForEntered: (String) -> Unit,
    onSubmitPriceClicked: () -> Unit
) {
    DefaultCardBackground(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            var text = stringResource(id = R.string.submit_new_price)
            var spacerHeight = 4.dp
            if (price != null) {
                PriceRows(
                    price = price,
                    nutritionValues = nutritionValues
                )
            } else {
                spacerHeight = 16.dp
                text =
                    stringResource(id = R.string.no_one_has_entered_price_for_this_product_be_the_first_one_to_do_so)
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

                DefaultCardBackground {
                    CustomBasicTextField(
                        value = priceValue,
                        onValueChange = {
                            onPriceValueEntered(it)
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
                        )
                    )
                }

                Text(
                    text = String.format(stringResource(id = R.string.for_x), currency),
                    style = MaterialTheme.typography.body1
                )

                DefaultCardBackground() {
                    CustomBasicTextField(
                        value = priceFor,
                        onValueChange = {
                            onForEntered(it)
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
                        )
                    )
                }

                Text(
                    text = unit,
                    style = MaterialTheme.typography.body1
                )

                IconButton(
                    onClick = {
                        onSubmitPriceClicked()
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