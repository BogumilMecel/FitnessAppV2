package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.components.DefaultCardBackground
import com.gmail.bogumilmecel2.fitnessappv2.components.defaultRoundedCornerShapeValue
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductPrice
import com.gmail.bogumilmecel2.ui.components.base.CustomIcon
import com.gmail.bogumilmecel2.ui.components.base.CustomIconButton
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.IconButtonParams
import com.gmail.bogumilmecel2.ui.components.base.IconVector
import com.gmail.bogumilmecel2.ui.components.base.WidthSpacer
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme
import com.gmail.bogumilmecel2.ui.theme.LocalColor.BlueViolet3
import com.gmail.bogumilmecel2.ui.theme.LocalColor.LightGreen3
import com.gmail.bogumilmecel2.ui.theme.LocalColor.OrangeYellow3

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
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.prices),
                    style = FitnessAppTheme.typography.HeaderSmall,
                    textAlign = TextAlign.Center,
                    color = FitnessAppTheme.colors.ContentPrimary,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                )

                CustomIconButton(
                    params = IconButtonParams(
                        iconVector = IconVector.Info,
                        onClick = { onInfoButtonClicked() }
                    ),
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }

            productPrice?.let {
                HorizontalDivider()

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
                            dividerColor = FitnessAppTheme.colors.Primary,
                            rightTextFirstLine = "100",
                            rightTextSecondLine = stringResource(id = R.string.calories)
                        )

                        HeightSpacer()

                        PriceItem(
                            priceValue = productPrice.valueFor100Carbohydrates,
                            dividerColor = LightGreen3,
                            rightTextFirstLine = stringResource(
                                id = R.string.measurement_unit_gram_with_value,
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
                                id = R.string.measurement_unit_gram_with_value,
                                10
                            ),
                            rightTextSecondLine = stringResource(id = R.string.protein)
                        )

                        HeightSpacer()

                        PriceItem(
                            priceValue = productPrice.valueFor10Fat,
                            dividerColor = OrangeYellow3,
                            rightTextFirstLine = stringResource(
                                id = R.string.measurement_unit_gram_with_value,
                                10
                            ),
                            rightTextSecondLine = stringResource(id = R.string.fat)
                        )
                    }
                }
            } ?: kotlin.run {
                Text(
                    text = stringResource(id = R.string.product_no_price),
                    style = FitnessAppTheme.typography.ParagraphMedium,
                    color = FitnessAppTheme.colors.ContentSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }

            HorizontalDivider()

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
                CustomIcon(icon = IconVector.Add)

                WidthSpacer(width = 8.dp)

                Text(
                    text = stringResource(id = R.string.submit_new_price),
                    style = FitnessAppTheme.typography.Button,
                    color = FitnessAppTheme.colors.Primary
                )
            }
        }
    }
}