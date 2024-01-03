package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import app.cash.sqldelight.EnumColumnAdapter
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DateAdapter
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DateTimeAdapter
import com.gmail.bogumilmecel2.fitnessappv2.database.SqlRecipeDiaryEntry

class RecipeDiaryEntryAdapter(
    private val nutritionValuesAdapter: NutritionValuesAdapter,
    private val longToIntAdapter: LongToIntAdapter,
    private val dateTimeAdapter: DateTimeAdapter,
    private val dateAdapter: DateAdapter
) {
    operator fun invoke() = SqlRecipeDiaryEntry.Adapter(
        nutrition_valuesAdapter = nutritionValuesAdapter(),
        meal_nameAdapter = EnumColumnAdapter(),
        servingsAdapter = longToIntAdapter(),
        creation_date_timeAdapter = dateTimeAdapter(),
        change_date_timeAdapter = dateTimeAdapter(),
        dateAdapter = dateAdapter()
    )
}