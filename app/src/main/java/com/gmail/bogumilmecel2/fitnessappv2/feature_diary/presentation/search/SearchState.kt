package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens.SearchItemParams

data class SearchState(
    val isLoading: Boolean = false,
    val searchBarText: String = "",
    val searchBarPlaceholderText: String = "",
    val isScannerVisible: Boolean = false,
    val hasPermissionDialogBeenShowed: Boolean = false,
    val mealName: MealName = MealName.BREAKFAST,
    val selectedTabIndex: Int = 0,
    val date: String,
    val everythingState: EverythingState = EverythingState(),
    val myRecipesState: MyRecipesState = MyRecipesState(),
    val myProductsState: MyProductsState = MyProductsState()
) {
    data class EverythingState(
        val searchItems: List<SearchItemParams> = emptyList(),
        val barcode: String? = null
    )

    data class MyProductsState(
        val searchItems: List<SearchItemParams> = emptyList()
    )

    data class MyRecipesState(
        val searchItems: List<SearchItemParams> = emptyList()
    )
}
