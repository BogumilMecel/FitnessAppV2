package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.DiaryEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.util.NutritionType


data class Meal(
    val mealName:String,
    val diaryEntries:List<DiaryEntry>
)

fun Meal.sumNutritionValues(
    nutritionType: NutritionType
):Double{
    val sum = when(nutritionType){
        is NutritionType.Calories -> {
            this.diaryEntries.sumOf {
                it.nutritionValues.calories
            }
        }
        is NutritionType.Carbs -> {
            this.diaryEntries.sumOf {
                it.nutritionValues.carbohydrates
            }
        }
        is NutritionType.Protein -> {
            this.diaryEntries.sumOf {
                it.nutritionValues.protein
            }
        }
        is NutritionType.Fat -> {
            this.diaryEntries.sumOf {
                it.nutritionValues.fat
            }
        }
    }
    return sum.toDouble()
}