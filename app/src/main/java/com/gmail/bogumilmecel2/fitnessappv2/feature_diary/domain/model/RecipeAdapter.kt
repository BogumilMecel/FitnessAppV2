package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.EnumColumnAdapter
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DateTimeAdapter
import com.gmail.bogumilmecel2.fitnessappv2.database.SqlRecipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Ingredient
import com.google.gson.Gson

class RecipeAdapter(
    private val gson: Gson,
    private val nutritionValuesAdapter: NutritionValuesAdapter,
    private val longToIntAdapter: LongToIntAdapter,
    private val dateTimeAdapter: DateTimeAdapter
) {
    operator fun invoke() = SqlRecipe.Adapter(
        ingredientsAdapter = object : ColumnAdapter<List<Ingredient>, String> {
            override fun decode(databaseValue: String) = gson.fromJson(databaseValue, Array<Ingredient>::class.java).asList()
            override fun encode(value: List<Ingredient>) = gson.toJson(value)
        },
        nutrition_valuesAdapter = nutritionValuesAdapter(),
        time_requiredAdapter = EnumColumnAdapter(),
        difficultyAdapter = EnumColumnAdapter(),
        servingsAdapter = longToIntAdapter(),
        creation_date_timeAdapter = dateTimeAdapter()
    )
}