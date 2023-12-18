package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Recipe

data class SearchState(
    val isLoading: Boolean = false,
    val items: List<Product> = emptyList(),
    val productSearchBarText: String = "",
    val recipesSearchBarText: String = "",
    val searchBarPlaceholderText: String,
    val isScannerVisible: Boolean = false,
    val hasPermissionDialogBeenShowed: Boolean = false,
    val barcode: String? = null,
    val mealName: String = "",
    val recipes: List<Recipe> = emptyList(),
    val currentTabIndex: Int = 0
)
