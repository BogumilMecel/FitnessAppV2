package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import app.cash.sqldelight.EnumColumnAdapter
import com.gmail.bogumilmecel2.fitnessappv2.database.SqlRecipeDiaryEntry

class RecipeDiaryEntryAdapter(
    private val nutritionValuesAdapter: NutritionValuesAdapter,
    private val longToIntAdapter: LongToIntAdapter
) {
    operator fun invoke() = SqlRecipeDiaryEntry.Adapter(
        nutrition_valuesAdapter = nutritionValuesAdapter(),
        meal_nameAdapter = EnumColumnAdapter(),
        servingsAdapter = longToIntAdapter()
    )
}