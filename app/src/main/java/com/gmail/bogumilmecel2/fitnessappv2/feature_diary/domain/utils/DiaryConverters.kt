package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.utils

import androidx.room.TypeConverter
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Ingredient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DiaryConverters {

    private val gson = Gson()

    @TypeConverter
    fun fromNutritionValuesToString(nutritionValues: NutritionValues): String =
        gson.toJson(nutritionValues)

    @TypeConverter
    fun fromStringToNutritionValues(src: String): NutritionValues = gson.fromJson(
        src,
        NutritionValues::class.java
    )

    @TypeConverter
    fun fromIngredientsToString(ingredients: List<Ingredient>): String =
        gson.toJson(ingredients)

    @TypeConverter
    fun fromStringToIngredients(src: String): List<Ingredient> = gson
        .fromJson(
            src,
            object : TypeToken<List<Ingredient>>() {}.type
        )
}