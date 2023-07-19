package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetPriceUseCase

data class ProductUseCases(
    val createPieChartData: CreatePieChartData,
    val calculateProductNutritionValuesUseCase: CalculateProductNutritionValuesUseCase,
    val insertProductDiaryEntryUseCase: InsertProductDiaryEntryUseCase,
    val submitNewPriceUseCase: SubmitNewPriceUseCase,
    val getPriceUseCase: GetPriceUseCase,
)
