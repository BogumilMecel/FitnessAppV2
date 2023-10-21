package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.Diary.KCAL
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.Diary.PRODUCT_NAME_1
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.Diary.PRODUCT_NAME_2
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.Diary.mockKcalWithValue
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.Diary.mockMeasurementUnitWithValueString
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.ui.components.complex.SearchItemParams
import org.junit.Test
import kotlin.test.assertEquals

class CreateSearchItemParamsFromProductDiaryEntryUseCaseTest: BaseTest() {

    private val createSearchItemParamsFromProductDiaryEntryUseCase = CreateSearchItemParamsFromProductDiaryEntryUseCase(resourceProvider)

    @Test
    fun `Check if params are correct`() {
        verifyParams(
            productName = PRODUCT_NAME_1,
            measurementUnit = MeasurementUnit.GRAMS,
            calories = 21,
            weight = 215
        )
    }

    @Test
    fun `Check if params are correct 2`() {
        verifyParams(
            productName = PRODUCT_NAME_2,
            measurementUnit = MeasurementUnit.MILLILITERS,
            calories = 400,
            weight = 373
        )
    }

    private fun verifyParams(
        productName: String,
        measurementUnit: MeasurementUnit,
        weight: Int,
        calories: Int
    ) {
        val nutritionValues = NutritionValues(calories = calories)

        mockMeasurementUnitWithValueString(
            resourceProvider = resourceProvider,
            value = weight
        )
        mockKcalWithValue(
            calories = calories,
            resourceProvider = resourceProvider
        )

        val productDiaryEntry = ProductDiaryEntry(
            productName = productName,
            nutritionValues = nutritionValues,
            productMeasurementUnit = measurementUnit,
            weight = weight
        )

        val onClick = {}
        val onLongClick = {}
        val weightText = when(productDiaryEntry.productMeasurementUnit) {
            MeasurementUnit.MILLILITERS -> "$weight${MockConstants.Diary.MILLILITERS}"
            MeasurementUnit.GRAMS -> "$weight${MockConstants.Diary.GRAMS}"
        }

        assertEquals(
            actual = createSearchItemParamsFromProductDiaryEntryUseCase(
                productDiaryEntry = productDiaryEntry,
                onClick = onClick,
                onLongClick = onLongClick
            ),
            expected = SearchItemParams(
                name = productName,
                textBelowName = weightText,
                endText = "${productDiaryEntry.nutritionValues.calories} $KCAL",
                onItemClick = onClick,
                onItemLongClick = onLongClick
            )
        )
    }
}