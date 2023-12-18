package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.BlueViolet2
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.BlueViolet3
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.LightGreen3
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.ui.theme.OrangeYellow2
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.round

@Composable
fun NutritionValuesList(
    modifier: Modifier,
    nutritionValues: NutritionValues
) {
    Column(
        modifier = modifier
    ){
        Text(
            text = stringResource(id = R.string.nutrition_values),
            style = MaterialTheme.typography.h3
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.carbohydrates),
                style = MaterialTheme.typography.body2.copy(
                    color = LightGreen3
                )
            )

            Text(
                text = "${nutritionValues.carbohydrates.round(2)}g",
                style = MaterialTheme.typography.body2.copy(
                    color = LightGreen3,
                    fontWeight = FontWeight.Medium
                )
            )

        }

        Spacer(modifier = Modifier.height(2.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.protein),
                style = MaterialTheme.typography.body2.copy(
                    color = BlueViolet3
                )
            )

            Text(
                text = "${nutritionValues.protein.round(2)}g",
                style = MaterialTheme.typography.body2.copy(
                    color = BlueViolet2,
                    fontWeight = FontWeight.Medium
                )
            )

        }

        Spacer(modifier = Modifier.height(2.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.fat),
                style = MaterialTheme.typography.body2.copy(
                    color = OrangeYellow2
                )
            )

            Text(
                text = "${nutritionValues.fat.round(2) }g",
                style = MaterialTheme.typography.body2.copy(
                    color = OrangeYellow2,
                    fontWeight = FontWeight.Medium
                )
            )

        }
    }
}