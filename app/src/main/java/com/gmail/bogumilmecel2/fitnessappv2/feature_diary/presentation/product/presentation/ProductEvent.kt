package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation

sealed interface ProductEvent {
    object ClickedBackArrow: ProductEvent
    object ClickedAddProduct: ProductEvent
    object SubmitNewPrice: ProductEvent
    object ClickedSubmitNewPrice: ProductEvent
    object ClickedInfoPriceButton: ProductEvent
    object DismissedSubmitNewPriceDialog: ProductEvent
    data class EnteredWeight(val value:String): ProductEvent
    data class EnteredPriceValue(val value:String): ProductEvent
    data class EnteredPriceFor(val value: String): ProductEvent
}