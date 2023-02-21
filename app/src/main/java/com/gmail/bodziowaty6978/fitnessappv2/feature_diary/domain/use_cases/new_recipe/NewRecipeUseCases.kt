package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.new_recipe

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.CalculatePricePerServingUseCase
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.product.CalculateProductNutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.product.CreatePieChartData
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProducts

data class NewRecipeUseCases(
    val addNewRecipe: AddNewRecipe,
    val calculatePrice: CalculatePrice,
    val calculatePricePerServingUseCase: CalculatePricePerServingUseCase,
    val calculateRecipeNutritionValues: CalculateRecipeNutritionValues,
    val createPieChartData: CreatePieChartData,
    val searchForProducts: SearchForProducts,
    val calculateProductNutritionValues: CalculateProductNutritionValues
)
