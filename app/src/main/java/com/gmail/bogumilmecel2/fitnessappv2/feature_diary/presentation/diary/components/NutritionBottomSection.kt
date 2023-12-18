package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary.components

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.BlueViolet3
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.LightGreen3
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.OrangeYellow3

@Composable
fun NutritionBottomSection(
    currentNutritionValues: NutritionValues,
    wantedNutritionValues: NutritionValues,
    modifier: Modifier
) {
    Row(
        modifier = modifier
    ) {
        NutritionBottomItem(
            currentValue =  currentNutritionValues.calories,
            wantedValue = wantedNutritionValues.calories,
            name = stringResource(id = R.string.calories),
            modifier = Modifier
                .weight(1F)
        )

        NutritionBottomItem(
            currentValue = currentNutritionValues.carbohydrates.toInt(),
            wantedValue = wantedNutritionValues.carbohydrates.toInt(),
            name = stringResource(id = R.string.carbs),
            modifier = Modifier
                .weight(1F),
            progressColor = LightGreen3
        )

        NutritionBottomItem(
            currentValue = currentNutritionValues.protein.toInt(),
            wantedValue = wantedNutritionValues.protein.toInt(),
            name = stringResource(id = R.string.protein),
            modifier = Modifier
                .weight(1F),
            progressColor = BlueViolet3
        )

        NutritionBottomItem(
            currentValue = currentNutritionValues.fat.toInt(),
            wantedValue = wantedNutritionValues.fat.toInt(),
            name = stringResource(id = R.string.fat),
            modifier = Modifier
                .weight(1F),
            progressColor = OrangeYellow3
        )
    }
    
}