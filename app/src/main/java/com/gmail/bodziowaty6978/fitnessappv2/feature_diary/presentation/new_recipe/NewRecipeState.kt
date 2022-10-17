package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Ingredient

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
    val name: String =  "",
    val ingredients: List<Ingredient> = emptyList(),
    val isSearchSectionVisible: Boolean = false,
    val searchItems:List<Product> = emptyList()
)
