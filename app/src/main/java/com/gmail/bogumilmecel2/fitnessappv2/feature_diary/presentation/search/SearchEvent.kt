package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe

sealed interface SearchEvent {
    data object ClickedBackArrow : SearchEvent
    data object ClickedScanButton : SearchEvent
    data object ClickedNewProduct : SearchEvent
    data object ClickedSearch : SearchEvent
    data class EnteredSearchText(val text: String) : SearchEvent
    data class ScannedBarcode(val code: String) : SearchEvent
    data class ClickedProduct(val product: Product) : SearchEvent
    data class ClickedRecipe(val recipe: Recipe) : SearchEvent
    data class SelectedTab(val index: Int) : SearchEvent
    data object ClickedCreateNewRecipe : SearchEvent
    data object ReachedListEnd : SearchEvent
}
