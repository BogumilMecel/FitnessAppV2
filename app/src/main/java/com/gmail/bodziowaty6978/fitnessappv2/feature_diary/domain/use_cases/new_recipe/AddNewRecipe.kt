package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.new_recipe

import com.gmail.bodziowaty6978.fitnessappv2.FitnessApp
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Ingredient
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class AddNewRecipe(
    private val diaryRepository: DiaryRepository,
    private val resourceProvider: ResourceProvider
) {
    suspend operator fun invoke(
        ingredients: List<Ingredient>,
        difficulty: String,
        time: String,
        servings: String,
        recipeName: String,
        nutritionValues: NutritionValues
    ): Resource<Recipe> {
        return if (ingredients.size < 2) {
            Resource.Error(resourceProvider.getString(R.string.empty_recipe_ingredients))
        } else if (recipeName.length < 4) {
            Resource.Error(resourceProvider.getString(R.string.bad_recipe_name))
        } else {
            val difficultyValue = difficulty.toIntOrNull()
            val timeValue = time.toIntOrNull()
            val servingsValue = servings.toIntOrNull()
            if (difficultyValue != null && timeValue != null && servingsValue != null) {
                val recipe = Recipe(
                    ingredients = ingredients,
                    timeNeeded = timeValue,
                    difficulty = difficultyValue,
                    servings = servingsValue,
                    timestamp = System.currentTimeMillis(),
                    name = recipeName,
                    username = FitnessApp.getUsername(),
                    nutritionValues = nutritionValues,
                    userId = FitnessApp.getUserId()
                )
                diaryRepository.addNewRecipe(
                    recipe = recipe,
                    timestamp = System.currentTimeMillis()
                )
            } else {
                Resource.Error(resourceProvider.getString(R.string.please_make_sure_all_fields_are_filled_in_correctly))
            }
        }
    }
}