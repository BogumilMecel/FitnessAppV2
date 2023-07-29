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
    val barcode: String? = null,
    val everythingSearchItems: List<SearchItemParams> = emptyList(),
    val myRecipesSearchItems: List<SearchItemParams> = emptyList(),
    val myProductsSearchItems: List<SearchItemParams> = emptyList()
)