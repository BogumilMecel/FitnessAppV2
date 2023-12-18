package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.diary

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Meal

data class DiaryState(
    val meals:List<Meal> = emptyList(),
    val errorMessage:String? = null,
    val lastErrorMessage:String? = null,
    val wantedNutritionValues:NutritionValues = NutritionValues()
)
