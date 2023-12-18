package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_product

sealed class NewProductEvent {
    object ClickedBackArrow:NewProductEvent()
    object ClickedDropDownMenu:NewProductEvent()
    object ClickedSaveButton:NewProductEvent()
    object ClosedScanner:NewProductEvent()
    object ClickedScannerButton:NewProductEvent()
    data class EnteredProductName(val value:String):NewProductEvent()
    data class ClickedDropDownMenuItem(val position:Int):NewProductEvent()
    data class ClickedNutritionTab(val position: Int):NewProductEvent()
    data class EnteredCalories(val calories:String):NewProductEvent()
    data class EnteredCarbohydrates(val carbohydrates:String):NewProductEvent()
    data class EnteredProtein(val protein:String):NewProductEvent()
    data class EnteredFat(val fat:String):NewProductEvent()
    data class EnteredContainerWeight(val containerWeight:String):NewProductEvent()
    data class EnteredBarcode(val barcode:String):NewProductEvent()



}