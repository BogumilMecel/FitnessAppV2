package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_recipe

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CalculateServingPrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromIngredientUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetRecipePriceFromIngredientsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.CalculateProductNutritionValuesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.CreatePieChartDataUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProductsUseCase

data class NewRecipeUseCases(
    val addNewRecipe: AddNewRecipe,
    val calculateRecipeNutritionValues: CalculateRecipeNutritionValues,
    val createPieChartDataUseCase: CreatePieChartDataUseCase,
    val searchForProductsUseCase: SearchForProductsUseCase,
    val calculateProductNutritionValuesUseCase: CalculateProductNutritionValuesUseCase,
    val getRecipePriceFromIngredientsUseCase: GetRecipePriceFromIngredientsUseCase,
    val calculateServingPrice: CalculateServingPrice,
    val createSearchItemParamsFromIngredientUseCase: CreateSearchItemParamsFromIngredientUseCase,
    val createSearchItemParamsFromProductUseCase: CreateSearchItemParamsFromProductUseCase
)
