package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.product

data class ProductUseCases(
    val createPieChartData: CreatePieChartData,
    val calculateProductNutritionValues: CalculateProductNutritionValues,
    val addDiaryEntry: AddDiaryEntry,
    val submitNewPriceUseCase: SubmitNewPriceUseCase
)
