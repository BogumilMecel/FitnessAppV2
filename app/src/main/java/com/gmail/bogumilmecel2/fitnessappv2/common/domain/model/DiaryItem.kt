package com.gmail.bogumilmecel2.fitnessappv2.common.domain.model

import androidx.compose.runtime.Composable
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntryType
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

interface DiaryItem {
    val id: String
    val nutritionValues: NutritionValues
    val userId: String
    val date: LocalDate
    val mealName: MealName
    val creationDateTime: LocalDateTime
    val changeDateTime: LocalDateTime

    @Composable
    fun getDisplayValue(): String

    fun getDiaryEntryType(): DiaryEntryType
}