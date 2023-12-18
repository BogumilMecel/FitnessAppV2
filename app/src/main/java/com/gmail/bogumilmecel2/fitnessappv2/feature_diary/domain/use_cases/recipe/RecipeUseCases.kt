package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CalculateSelectedServingPriceUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromIngredientUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetRecipePriceFromIngredientsUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.CreatePieChartDataUseCase

data class RecipeUseCases(
    val createPieChartDataUseCase: CreatePieChartDataUseCase,
    val postRecipeDiaryEntryUseCase: PostRecipeDiaryEntryUseCase,
    val getRecipePriceFromIngredientsUseCase: GetRecipePriceFromIngredientsUseCase,
    val calculateSelectedServingPriceUseCase: CalculateSelectedServingPriceUseCase,
    val editRecipeDiaryEntryUseCase: EditRecipeDiaryEntryUseCase,
    val createSearchItemParamsFromIngredientUseCase: CreateSearchItemParamsFromIngredientUseCase,
)