package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import app.cash.sqldelight.EnumColumnAdapter
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DateTimeAdapter
import com.gmail.bogumilmecel2.fitnessappv2.database.SqlProduct

class ProductAdapter(
    private val nutritionValuesAdapter: NutritionValuesAdapter,
    private val longToIntAdapter: LongToIntAdapter,
    private val dateTimeAdapter: DateTimeAdapter
) {
    operator fun invoke() = SqlProduct.Adapter(
        container_weightAdapter = longToIntAdapter(),
        nutrition_values_inAdapter = EnumColumnAdapter(),
        measurement_unitAdapter = EnumColumnAdapter(),
        nutrition_valuesAdapter = nutritionValuesAdapter(),
        creation_date_timeAdapter = dateTimeAdapter()
    )
}