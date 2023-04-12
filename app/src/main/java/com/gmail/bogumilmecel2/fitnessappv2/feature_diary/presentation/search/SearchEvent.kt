package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe

sealed interface SearchEvent {
    object ShowedPermissionDialog : SearchEvent
    object ClickedBackArrow : SearchEvent
    object ClickedScanButton : SearchEvent
    object ClickedNewProduct : SearchEvent
    object ClickedSearch : SearchEvent
    data class EnteredSearchText(val text: String) : SearchEvent
    data class ScannedBarcode(val code: String) : SearchEvent
    data class ClickedProduct(val product: Product) : SearchEvent
    data class ClickedRecipe(val recipe: Recipe) : SearchEvent
    object ClosedScanner : SearchEvent
    object ClickedCreateNewRecipe : SearchEvent
    object ClickedRecipesTab : SearchEvent
    object ClickedProductsTab : SearchEvent
}
