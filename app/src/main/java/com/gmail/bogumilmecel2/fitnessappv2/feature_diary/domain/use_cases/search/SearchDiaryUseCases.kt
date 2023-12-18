package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromRecipeUseCase

data class SearchDiaryUseCases(
    val searchForProductsUseCase: SearchForProductsUseCase,
    val searchForProductWithBarcode: SearchForProductWithBarcode,
    val searchForRecipes: SearchForRecipes,
    val createSearchItemParamsFromRecipeUseCase: CreateSearchItemParamsFromRecipeUseCase
)
