package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.domain.model

import com.github.tehras.charts.piechart.PieChartData
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues

data class NutritionData(
    val nutritionValues: NutritionValues = NutritionValues(),
    val pieChartData: PieChartData = PieChartData(slices = emptyList())
)

