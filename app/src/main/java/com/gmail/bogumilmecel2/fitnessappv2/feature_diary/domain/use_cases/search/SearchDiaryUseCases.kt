package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromHistoryItemUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromRecipeUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.GetProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.GetRecipeUseCase

data class SearchDiaryUseCases(
    val searchForProductsUseCase: SearchForProductsUseCase,
    val searchForProductWithBarcode: SearchForProductWithBarcode,
    val searchForRecipes: SearchForRecipes,
    val createSearchItemParamsFromRecipeUseCase: CreateSearchItemParamsFromRecipeUseCase,
    val createSearchItemParamsFromProductUseCase: CreateSearchItemParamsFromProductUseCase,
    val createSearchItemParamsFromHistoryItemUseCase: CreateSearchItemParamsFromHistoryItemUseCase,
    val getRecipeUseCase: GetRecipeUseCase,
    val getProductUseCase: GetProductUseCase
)
