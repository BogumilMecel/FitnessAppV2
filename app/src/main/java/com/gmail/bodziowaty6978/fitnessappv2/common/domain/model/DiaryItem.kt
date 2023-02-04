package com.gmail.bodziowaty6978.fitnessappv2.common.domain.model

interface DiaryItem {
    val id: String
    val nutritionValues: NutritionValues
    val timestamp: Long
    val userId: String
    val date: String
    val mealName: String
}