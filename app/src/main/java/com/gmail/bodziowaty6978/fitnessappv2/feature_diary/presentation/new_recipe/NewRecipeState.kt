package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Price
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Ingredient
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.util.SelectedNutritionType
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components.NutritionData

data class NewRecipeState(
    val isDifficultyExpanded: Boolean = false,
    val isTimeExpanded: Boolean = false,
    val isServingsExpanded: Boolean = false,
    val difficulties: List<String> = emptyList(),
    val times: List<String> = emptyList(),
    val selectedDifficulty: String = "1",
    val selectedTime: String = "15",
    val servings: String = "1",
    val name: String = "",
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
    val shouldShowPriceWarning: Boolean = false
)
