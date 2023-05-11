package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search

data class SearchDiaryUseCases(
    val getDiaryHistory: GetDiaryHistory,
    val searchForProductsUseCase: SearchForProductsUseCase,
    val searchForProductWithBarcode: SearchForProductWithBarcode,
    val searchForRecipes: SearchForRecipes
)
