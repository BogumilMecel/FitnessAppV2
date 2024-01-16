package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_product

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit

sealed interface NewProductEvent {
    object ClickedBackArrow:NewProductEvent
    object ClickedDropDownMenu:NewProductEvent
    object ClickedSaveButton:NewProductEvent
    object ClickedScannerButton:NewProductEvent
    data class SelectedMeasurementUnit(val measurementUnit: MeasurementUnit):NewProductEvent
    data class ClickedNutritionTab(val position: Int):NewProductEvent
}