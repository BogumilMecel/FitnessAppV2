package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.search

sealed class SearchEvent{
    object ShowedPermissionDialog:SearchEvent()
    object ClickedBackArrow:SearchEvent()
    object ClickedScanButton:SearchEvent()
    object ClickedNewProduct:SearchEvent()
    object ClickedSearch:SearchEvent()
    data class EnteredSearchText(val text:String):SearchEvent()
    data class ScannedBarcode(val code:String):SearchEvent()
    data class ClickedSearchItem(val item:ProductWithId):SearchEvent()
    object ClosedScanner:SearchEvent()
}
