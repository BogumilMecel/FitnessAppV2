package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product

sealed interface ProductEvent {
    object ClickedBackArrow:ProductEvent
    object ClickedAddProduct:ProductEvent
    object ClickedSubmitNewPrice:ProductEvent
    data class EnteredWeight(val value:String):ProductEvent
    data class EnteredPriceValue(val value:String):ProductEvent
    data class EnteredPriceFor(val value: String):ProductEvent
}