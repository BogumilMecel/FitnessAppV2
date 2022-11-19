package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.product

import com.github.mikephil.charting.data.PieEntry
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.CalculateNutritionValuesPercentages

class CreatePieChartData(
    private val calculateNutritionValuesPercentages: CalculateNutritionValuesPercentages
) {

    operator fun invoke(nutritionValues: NutritionValues): List<PieEntry> {
        val calculatedPercentages = calculateNutritionValuesPercentages(nutritionValues)
        val entries = mutableListOf<PieEntry>()

        calculatedPercentages.keys.forEach { key ->
            calculatedPercentages[key]?.let {
                entries.add(PieEntry(it, key))
                entries.add(PieEntry(1F, ""))
            }
        }
        return entries
    }
}