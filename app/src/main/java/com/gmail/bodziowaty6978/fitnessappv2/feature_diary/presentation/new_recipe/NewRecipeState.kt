package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Price
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Ingredient
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.utils.Difficulty
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.utils.TimeRequired
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.util.SelectedNutritionType
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components.NutritionData

data class NewRecipeState(
    val isDifficultyExpanded: Boolean = false,
    val isTimeExpanded: Boolean = false,
    val isServingsExpanded: Boolean = false,
    val difficulties: List<Difficulty> = Difficulty.values().toList(),
    val times: List<TimeRequired> = TimeRequired.values().toList(),
    val selectedDifficulty: Difficulty = Difficulty.LOW,
    val selectedTime: TimeRequired = TimeRequired.LOW,
    val servings: String = "1",
    val name: String = "",
    val mealName: String,
    val ingredients: List<Ingredient> = emptyList(),
    val isSearchSectionVisible: Boolean = false,
    val searchItems: List<Product> = emptyList(),
    val searchText: String = "",
    val isProductSectionVisible: Boolean = false,
    val isRecipeSectionVisible: Boolean = true,
    val selectedProduct: Product? = null,
    val nutritionData: NutritionData = NutritionData(),
    val productWeight: String = "",
    val selectedNutritionType: SelectedNutritionType = SelectedNutritionType.Recipe,
    val recipePrice: Price? = null,
    val isRecipePublic: Boolean = true,
    val isIngredientsListExpanded: Boolean = true,
)
