package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.EnumColumnAdapter
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.database.SqlProduct
import com.google.gson.Gson
import kotlinx.datetime.LocalDateTime

class ProductAdapter(private val gson: Gson) {
    operator fun invoke() = SqlProduct.Adapter(
        container_weightAdapter = object : ColumnAdapter<Int, Long> {
            override fun decode(databaseValue: Long) = databaseValue.toInt()
            override fun encode(value: Int) = value.toLong()
        },
        nutrition_values_inAdapter = EnumColumnAdapter(),
        measurement_unitAdapter = EnumColumnAdapter(),
        nutrition_valuesAdapter = object : ColumnAdapter<NutritionValues, String> {
            override fun decode(databaseValue: String) = gson.fromJson(databaseValue, NutritionValues::class.java)
            override fun encode(value: NutritionValues) = gson.toJson(value)
        },
        date_createdAdapter = object : ColumnAdapter<LocalDateTime, String> {
            override fun decode(databaseValue: String) = LocalDateTime.parse(databaseValue)
            override fun encode(value: LocalDateTime) = value.toString()
        }
    )
}