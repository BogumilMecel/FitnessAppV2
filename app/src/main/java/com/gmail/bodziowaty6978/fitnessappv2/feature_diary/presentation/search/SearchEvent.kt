package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.ProductWithId

sealed class SearchEvent{
    object ClickedBackArrow:SearchEvent()
    object ClickedScanButton:SearchEvent()
    data class ClickedNewProduct(val mealName:String):SearchEvent()
    object ClickedSearch:SearchEvent()
    data class EnteredSearchText(val text:String):SearchEvent()
    data class ScannedBarcode(val code:String):SearchEvent()
    data class ClickedSearchItem(val item:ProductWithId, val mealName:String):SearchEvent()
}
