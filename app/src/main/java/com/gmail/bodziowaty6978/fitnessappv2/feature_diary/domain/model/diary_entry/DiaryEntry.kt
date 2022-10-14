package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import kotlinx.serialization.Serializable

@Serializable
data class DiaryEntry(
    val id:Int = -1,
    val product: Product,
    val timestamp:Long,
    val date:String,
    var weight:Int,
    val mealName:String
)

fun DiaryEntry.calculateNutritionValues():CalculatedDiaryEntry{
    return CalculatedDiaryEntry(
        diaryEntry = this,
        nutritionValues = NutritionValues(
            calories = (this.product.nutritionValues.calories.toDouble()/100.0*this.weight).toInt(),
            carbohydrates = this.product.nutritionValues.carbohydrates/100.0*this.weight,
            protein = this.product.nutritionValues.protein/100.0*this.weight,
            fat = this.product.nutritionValues.fat/100.0*this.weight
        )
    )
}