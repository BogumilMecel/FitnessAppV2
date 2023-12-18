package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product

sealed interface SearchEvent {
    object ShowedPermissionDialog : SearchEvent
    object ClickedBackArrow : SearchEvent
    object ClickedScanButton : SearchEvent
    object ClickedNewProduct : SearchEvent
    object ClickedSearch : SearchEvent
    data class EnteredSearchText(val text: String) : SearchEvent
    data class ScannedBarcode(val code: String) : SearchEvent
    data class ClickedSearchItem(val item: Product) : SearchEvent
    object ClosedScanner : SearchEvent
    object ClickedCreateNewRecipe : SearchEvent
    object ClickedRecipesTab : SearchEvent
    object ClickedProductsTab : SearchEvent
}
