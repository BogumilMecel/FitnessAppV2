package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product

import com.github.tehras.charts.piechart.PieChartData
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValue
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.CalculateNutritionValuesPercentages
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.BlueViolet3
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.DarkGreyElevation2
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.LightGreen3
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.ui.theme.OrangeYellow3

class CreatePieChartData(
    private val calculateNutritionValuesPercentages: CalculateNutritionValuesPercentages
) {
    operator fun invoke(nutritionValues: NutritionValues): PieChartData {
        val slices = mutableListOf<PieChartData.Slice>()
        val calculatedNutritionValues = calculateNutritionValuesPercentages(nutritionValues)
        calculatedNutritionValues.forEach {
            if (it.value != 0f) {
                slices.add(
                    PieChartData.Slice(
                        value = it.value,
                        color = getColorForNutritionValue(nutritionValue = it.key)
                    )
                )
                if (calculatedNutritionValues.values.count { value -> value == 0f } != 2) {
                    slices.add(getBackgroundSlice())
                }
            }
        }

        return PieChartData(
            slices = slices
        )
    }

    private fun getBackgroundSlice() = PieChartData.Slice(
        value = 1f,
        color = DarkGreyElevation2
    )

    private fun getColorForNutritionValue(nutritionValue: NutritionValue) = when (nutritionValue) {
        NutritionValue.CARBOHYDRATES -> LightGreen3
        NutritionValue.PROTEIN -> BlueViolet3
        NutritionValue.FAT -> OrangeYellow3
    }
}