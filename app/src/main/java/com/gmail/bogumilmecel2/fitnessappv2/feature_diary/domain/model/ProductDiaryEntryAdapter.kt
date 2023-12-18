package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import app.cash.sqldelight.EnumColumnAdapter
import com.gmail.bogumilmecel2.fitnessappv2.database.SqlProductDiaryEntry

class ProductDiaryEntryAdapter(
    private val nutritionValuesAdapter: NutritionValuesAdapter,
    private val longToIntAdapter: LongToIntAdapter
) {
    operator fun invoke() = SqlProductDiaryEntry.Adapter(
        nutrition_valuesAdapter = nutritionValuesAdapter(),
        meal_nameAdapter = EnumColumnAdapter(),
        product_measurement_unitAdapter = EnumColumnAdapter(),
        weightAdapter = longToIntAdapter()
    )
}