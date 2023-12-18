package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product

sealed class ProductEvent {
    object ClickedBackArrow:ProductEvent()
    data class ClickedAddProduct(val product: Product):ProductEvent()
    data class EnteredWeight(val value:String, val product: Product):ProductEvent()
}