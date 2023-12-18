package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import app.cash.sqldelight.ColumnAdapter
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.google.gson.Gson

class NutritionValuesAdapter(private val gson: Gson) {
    operator fun invoke() = object : ColumnAdapter<NutritionValues, String> {
        override fun decode(databaseValue: String) = gson.fromJson(databaseValue, NutritionValues::class.java)
        override fun encode(value: NutritionValues) = gson.toJson(value)
    }
}