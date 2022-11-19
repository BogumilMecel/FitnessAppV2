package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.edit_nutrition_goals

sealed interface EditNutritionGoalsEvent{
    object BackArrowPressed:EditNutritionGoalsEvent
    object SaveButtonClicked:EditNutritionGoalsEvent
    data class EnteredCalories(val value:String):EditNutritionGoalsEvent
    data class CarbohydratesPickerValueChanged(val value:String):EditNutritionGoalsEvent
    data class ProteinPickerValueChanged(val value:String):EditNutritionGoalsEvent
    data class FatPickerValueChanged(val value:String):EditNutritionGoalsEvent
}