package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.WidthSpacer
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.BlueViolet3
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.LightGreen3
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.OrangeYellow3
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.TextGrey
import com.gmail.bogumilmecel2.fitnessappv2.components.DefaultCardBackground
import com.gmail.bogumilmecel2.fitnessappv2.components.defaultRoundedCornerShapeValue
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductPrice

@Composable
fun PriceSection(
    modifier: Modifier,
    productPrice: ProductPrice?,
    onSubmitPriceClicked: () -> Unit,
    onInfoButtonClicked: () -> Unit
) {
    DefaultCardBackground(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.prices),
                    style = MaterialTheme.typography.h3.copy(
                        textAlign = TextAlign.Start
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                )

                IconButton(
                    onClick = {
                        onInfoButtonClicked()
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "info",
                        tint = MaterialTheme.colors.primary
                    )
                }
            }

            productPrice?.let {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PriceItem(
                            priceValue = productPrice.valueFor100Calories,
                            dividerColor = MaterialTheme.colors.primary,
                            rightTextFirstLine = "100",
                            rightTextSecondLine = stringResource(id = R.string.calories)
                        )

                        HeightSpacer()

                        PriceItem(
                            priceValue = productPrice.valueFor100Carbohydrates,
                            dividerColor = LightGreen3,
                            rightTextFirstLine = stringResource(
                                id = R.string.value_with_short_grams,
                                100
                            ),
                            rightTextSecondLine = stringResource(id = R.string.carbs)
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PriceItem(
                            priceValue = productPrice.valueFor10Protein,
                            dividerColor = BlueViolet3,
                            rightTextFirstLine = stringResource(
                                id = R.string.value_with_short_grams,
                                10
                            ),
                            rightTextSecondLine = stringResource(id = R.string.protein)
                        )

                        HeightSpacer()

                        PriceItem(
                            priceValue = productPrice.valueFor10Fat,
                            dividerColor = OrangeYellow3,
                            rightTextFirstLine = stringResource(
                                id = R.string.value_with_short_grams,
                                10
                            ),
                            rightTextSecondLine = stringResource(id = R.string.fat)
                        )
                    }
                }
            } ?: kotlin.run {
                Text(
                    text = stringResource(id = R.string.product_no_price),
                    style = MaterialTheme.typography.body2.copy(
                        color = TextGrey,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            bottomEnd = defaultRoundedCornerShapeValue(),
                            bottomStart = defaultRoundedCornerShapeValue(),
                            topStart = 0.dp,
                            topEnd = 0.dp
                        )
                    )
                    .clickable { onSubmitPriceClicked() }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add",
                    tint = MaterialTheme.colors.primary
                )

                WidthSpacer(width = 8.dp)

                Text(
                    text = stringResource(id = R.string.submit_new_price),
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}