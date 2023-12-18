package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.ProductEvent
import com.gmail.bogumilmecel2.ui.components.base.ButtonParams
import com.gmail.bogumilmecel2.ui.components.base.CustomBasicTextField
import com.gmail.bogumilmecel2.ui.components.base.CustomDialog
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.base.WidthSpacer
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun SubmitNewPriceDialog(
    productName: String,
    price: String,
    forValue: String,
    currency: Currency,
    measurementUnit: MeasurementUnit,
    onEvent: (ProductEvent) -> Unit,
) {
    CustomDialog(
        title = stringResource(
            id = R.string.price_how_much_pay,
            productName
        ),
        content = {
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.paid),
                        style = MaterialTheme.typography.body1
                    )

                    WidthSpacer(width = 4.dp)

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomBasicTextField(
                            value = price,
                            onValueChange = {
                                onEvent(ProductEvent.EnteredPriceValue(it))
                            },
                            modifier = Modifier.width(80.dp),
                            singleLine = true,
                            textAlign = TextAlign.Center,
                            placeholder = "5.99",
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            )
                        )

                        WidthSpacer(width = 4.dp)

                        Text(
                            text = stringResource(id = currency.getDisplayValue()),
                            style = MaterialTheme.typography.body2.copy(
                                color = FitnessAppTheme.colors.ContentSecondary
                            )
                        )
                    }
                }

                HeightSpacer(height = 8.dp)

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.price_for),
                        style = MaterialTheme.typography.body1
                    )

                    WidthSpacer(width = 4.dp)

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomBasicTextField(
                            value = forValue,
                            onValueChange = {
                                onEvent(ProductEvent.EnteredPriceFor(it))
                            },
                            modifier = Modifier.width(80.dp),
                            singleLine = true,
                            textAlign = TextAlign.Center,
                            placeholder = "500",
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            )
                        )

                        WidthSpacer(width = 4.dp)

                        Text(
                            text = stringResource(id = measurementUnit.getStringRes()),
                            style = MaterialTheme.typography.body2.copy(
                                color = FitnessAppTheme.colors.ContentSecondary
                            )
                        )
                    }
                }
            }
        },
        endButtonParams = ButtonParams(
            text = stringResource(id = R.string.submit),
            onClick = { onEvent(ProductEvent.SubmitNewPrice) }
        ),
        secondaryButtonParams = ButtonParams(
            text = stringResource(id = R.string.cancel),
            onClick = { onEvent(ProductEvent.DismissedSubmitNewPriceDialog) }
        ),
        onDismissRequest = { onEvent(ProductEvent.DismissedSubmitNewPriceDialog) }
    )
}