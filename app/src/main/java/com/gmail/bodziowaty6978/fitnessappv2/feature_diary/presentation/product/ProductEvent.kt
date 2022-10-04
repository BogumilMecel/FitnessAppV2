package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product

sealed interface ProductEvent {
    object ClickedBackArrow:ProductEvent
    object ClickedAddProduct:ProductEvent
    object ClickedSubmitNewPrice:ProductEvent
    data class EnteredWeight(val value:String, val product: Product):ProductEvent
    data class EnteredPriceValue(val value:String):ProductEvent
    data class EnteredPriceFor(val value: String):ProductEvent
}