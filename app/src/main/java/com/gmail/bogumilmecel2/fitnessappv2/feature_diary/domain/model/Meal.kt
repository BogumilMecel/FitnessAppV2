package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.util.NutritionType


data class Meal(
    val mealName: MealName,
    val diaryEntries: List<DiaryItem>
)

fun Meal.sumNutritionValues(
    nutritionType: NutritionType
): Double {
    val sum = when (nutritionType) {
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