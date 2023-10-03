package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Ingredient
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipePrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.utils.Difficulty
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.utils.TimeRequired
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe.util.SelectedNutritionType
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.domain.model.NutritionData
import com.gmail.bogumilmecel2.ui.components.complex.SearchItemParams

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
    val ingredientsItemsParams: List<SearchItemParams> = listOf(),
    val nutritionData: NutritionData = NutritionData(),
    val selectedNutritionType: SelectedNutritionType = SelectedNutritionType.Recipe,
    val recipePrice: RecipePrice? = null,
    val servingPrice: Double? = null,
    val isRecipePublic: Boolean = true,
    val isIngredientsListExpanded: Boolean = true,
    val longClickedIngredient: Ingredient? = null,
    val isDeleteIngredientDialogVisible: Boolean = false,
)
