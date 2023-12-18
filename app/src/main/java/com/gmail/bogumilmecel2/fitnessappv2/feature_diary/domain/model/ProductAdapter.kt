package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.EnumColumnAdapter
import com.gmail.bogumilmecel2.fitnessappv2.database.SqlProduct
import kotlinx.datetime.LocalDateTime

class ProductAdapter(
    private val nutritionValuesAdapter: NutritionValuesAdapter,
    private val longToIntAdapter: LongToIntAdapter
) {
    operator fun invoke() = SqlProduct.Adapter(
        container_weightAdapter = longToIntAdapter(),
        nutrition_values_inAdapter = EnumColumnAdapter(),
        measurement_unitAdapter = EnumColumnAdapter(),
        nutrition_valuesAdapter = nutritionValuesAdapter(),
        date_createdAdapter = object : ColumnAdapter<LocalDateTime, String> {
            override fun decode(databaseValue: String) = LocalDateTime.parse(databaseValue)
            override fun encode(value: LocalDateTime) = value.toString()
        }
    )
}