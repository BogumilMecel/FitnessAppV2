package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromProductDiaryEntryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CreateSearchItemParamsFromRecipeUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GenerateNewRecipeSearchTitleUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetDiaryHistoryUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.ShouldDisplayNextPageUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.GetProductUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe.GetRecipeUseCase

data class SearchDiaryUseCases(
    val searchForProductsUseCase: SearchForProductsUseCase,
    val searchForProductWithBarcode: SearchForProductWithBarcode,
    val searchForRecipes: SearchForRecipes,
    val createSearchItemParamsFromRecipeUseCase: CreateSearchItemParamsFromRecipeUseCase,
    val createSearchItemParamsFromProductUseCase: CreateSearchItemParamsFromProductUseCase,
    val createSearchItemParamsFromProductDiaryEntryUseCase: CreateSearchItemParamsFromProductDiaryEntryUseCase,
    val getRecipeUseCase: GetRecipeUseCase,
    val getProductUseCase: GetProductUseCase,
    val getDiaryHistoryUseCase: GetDiaryHistoryUseCase,
    val shouldDisplayNextPageUseCase: ShouldDisplayNextPageUseCase,
    val getOfflineUserRecipesUseCase: GetOfflineUserRecipesUseCase,
    val getOfflineUserProductsUseCase: GetOfflineUserProductsUseCase,
    val generateNewRecipeSearchTitleUseCase: GenerateNewRecipeSearchTitleUseCase
)
