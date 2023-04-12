package com.gmail.bogumilmecel2.fitnessappv2.common.domain.model

import androidx.compose.runtime.Composable
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntryType
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName

interface DiaryItem {
    val id: String
    val nutritionValues: NutritionValues
    val timestamp: Long
    val userId: String
    val date: String
    val mealName: MealName

    @Composable
    fun getDisplayValue(): String

    fun getDiaryEntryType(): DiaryEntryType
}