package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.ui.theme.FitnessAppTheme

@Composable
fun PriceItem(
    modifier: Modifier = Modifier,
    priceValue: Double?,
    currency: String = "zł",
    dividerColor: Color,
    rightTextFirstLine: String,
    rightTextSecondLine: String?
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (divider, value, column) = createRefs()

        Text(
            text = priceValue?.let { "$priceValue $currency" } ?: stringResource(id = R.string.not_available),
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.Medium,
                color = priceValue?.let { FitnessAppTheme.colors.ContentPrimary } ?: FitnessAppTheme.colors.ContentSecondary
            ),
            modifier = Modifier
                .constrainAs(value) {
                    end.linkTo(divider.start)
                    centerVerticallyTo(divider)
                }
                .padding(end = 4.dp)
        )

        Box(
            modifier = Modifier
                .width(4.dp)
                .height(32.dp)
                .clip(RoundedCornerShape(50))
                .background(color = dividerColor)
                .constrainAs(divider) {
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                    top.linkTo(parent.top)
                }
        )

        Column(
            modifier = Modifier
                .constrainAs(column) {
                    start.linkTo(divider.end)
                    centerVerticallyTo(divider)
                }
                .padding(start = 4.dp)
        ) {
            Text(
                text = rightTextFirstLine,
                style = MaterialTheme.typography.body2.copy(
                    color = FitnessAppTheme.colors.ContentSecondary
                )
            )

            rightTextSecondLine?.let {
                Text(
                    text = rightTextSecondLine,
                    style = MaterialTheme.typography.body2.copy(
                        color = FitnessAppTheme.colors.ContentSecondary
                    )
                )
            }
        }
    }
}