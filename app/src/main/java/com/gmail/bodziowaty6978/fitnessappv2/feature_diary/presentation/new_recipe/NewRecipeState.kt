package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe

data class NewRecipeState(
    val isDifficultyExpanded: Boolean = false,
    val isTimeExpanded: Boolean = false,
    val isServingsExpanded: Boolean = false,
    val recipeName: String = "",
    val difficulties: List<String> = emptyList(),
    val times: List<String> = emptyList(),
    val selectedDifficulty: String = "1",
    val selectedTime: String = "15",
    val servings: String = "1",
    val name: String =  ""

)
