package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_product

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.State
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.product.NutritionValuesIn

data class NewProductState(
    val isDropDownMenuExpanded: Boolean = false,
    val measurementUnits: List<MeasurementUnit> = MeasurementUnit.values().toList(),
    val selectedMeasurementUnit: MeasurementUnit = MeasurementUnit.GRAMS,
    val productName: String = "",
    val containerWeightValue: String = "",
    val calories: String = "",
    val carbohydrates: String = "",
    val protein: String = "",
    val fat: String = "",
    val nutritionValuesInTabs: List<NutritionValuesIn> = NutritionValuesIn.values().toList(),
    val nutritionValuesInSelectedTabIndex: Int = 0,
    val barcode: String = "",
    val isLoading: Boolean = false,
    val isScannerVisible: Boolean = false,
    val mealName: String
): State


