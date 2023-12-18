package com.gmail.bogumilmecel2.fitnessappv2.common.domain.model

interface CachedValuesProvider {
    fun getWantedNutritionValues(): NutritionValues
    fun saveWantedNutritionValues(nutritionValues: NutritionValues)
    fun getWeightProgress(): String
}