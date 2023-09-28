package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.ui.components.complex.SearchItemParams

data class SearchState(
    val searchBarText: String = "",
    val searchBarPlaceholderText: String = "",
    val mealName: MealName = MealName.BREAKFAST,
    val selectedTabIndex: Int = SearchTab.EVERYTHING.ordinal,
    val date: String = "",
    val everythingSearchItems: List<SearchItemParams> = emptyList(),
    val myRecipesSearchItems: List<SearchItemParams> = emptyList(),
    val myProductsSearchItems: List<SearchItemParams> = emptyList(),
    val searchButtonVisible: Boolean = true,
    val noProductFoundVisible: Boolean = false
)