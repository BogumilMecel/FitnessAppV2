package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search

import com.gmail.bogumilmecel2.ui.components.complex.SearchItemParams

data class SearchState(
    val searchBarText: String = "",
    val searchBarPlaceholderText: String = "",
    val headerPrimaryText: String = "",
    val headerSecondaryText: String? = null,
    val selectedTabIndex: Int = SearchTab.EVERYTHING.ordinal,
    val everythingSearchItems: List<SearchItemParams> = emptyList(),
    val myRecipesSearchItems: List<SearchItemParams> = emptyList(),
    val myProductsSearchItems: List<SearchItemParams> = emptyList(),
    val searchButtonVisible: Boolean = true,
    val noProductFoundVisible: Boolean = false,
    val recipeTabVisible: Boolean = true
)