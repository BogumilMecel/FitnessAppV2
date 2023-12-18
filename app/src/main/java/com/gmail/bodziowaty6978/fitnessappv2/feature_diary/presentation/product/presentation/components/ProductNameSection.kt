package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.CustomBasicTextField
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.DefaultCardBackground
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.BlueViolet3
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.DarkGreyElevation
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.TextGrey
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.TextWhite
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product

@Composable
fun ProductNameSection(
    product: Product,
    currentWeight: String,
    modifier: Modifier,
    onWeightEntered: (String) -> Unit
) {
    DefaultCardBackground(modifier = modifier,) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = stringResource(id = R.string.product_name),
                        style = MaterialTheme.typography.h3
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row {
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.body2.copy(
                                color = TextWhite
                            ),
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DefaultCardBackground() {
                        CustomBasicTextField(
                            value = currentWeight,
                            onValueChange = {
                                onWeightEntered(it)
                            },
                            modifier = Modifier
                                .width(90.dp)
                                .padding(
                                    horizontal = 8.dp
                                )
                                .testTag(stringResource(id = R.string.WEIGHT_TEXT_FIELD)),
                            textStyle = MaterialTheme.typography.body1.copy(
                                color = TextWhite,
                                textAlign = TextAlign.End
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                        )
                    }


                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = stringResource(id = R.string.g),
                        style = MaterialTheme.typography.body2.copy(
                            color = TextGrey
                        )
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 13.dp)
                    .padding(bottom = 15.dp)
            ) {

                for (i in 50..200 step 50) {
                    OutlinedButton(
                        onClick = {
                            onWeightEntered(i.toString())
                        },
                        border = BorderStroke(1.dp, BlueViolet3),
                        shape = RoundedCornerShape(25),
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = DarkGreyElevation
                        ),
                        modifier = Modifier
                            .testTag(i.toString() + stringResource(id = R.string.WEIGHT_BUTTON))
                    ) {
                        Text(
                            text = "${i}g",
                            color = BlueViolet3,
                            style = MaterialTheme.typography.button
                        )
                    }
                }
            }
        }
    }
}