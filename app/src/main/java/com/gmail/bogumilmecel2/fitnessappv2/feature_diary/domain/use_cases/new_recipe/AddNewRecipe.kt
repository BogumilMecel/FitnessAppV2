package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_recipe

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Ingredient
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.NewRecipeRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.utils.Difficulty
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.utils.TimeRequired
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class AddNewRecipe(
    private val diaryRepository: DiaryRepository,
    private val resourceProvider: ResourceProvider
) {
    suspend operator fun invoke(
        ingredients: List<Ingredient>,
        difficulty: Difficulty,
        time: TimeRequired,
        servings: String,
        recipeName: String,
        isRecipePublic: Boolean
    ): Resource<Recipe> {
        return if (ingredients.size < 2) {
            Resource.Error(resourceProvider.getString(R.string.empty_recipe_ingredients))
        } else if (recipeName.length < 4) {
            Resource.Error(resourceProvider.getString(R.string.bad_recipe_name))
        } else {
            servings.toIntOrNull()?.let { servingsValue ->
                val newRecipeRequest = NewRecipeRequest(
                    ingredients = ingredients,
                    timeRequired = time,
                    difficulty = difficulty,
                    servings = servingsValue,
                    recipeName = recipeName,
                    isPublic = isRecipePublic
                )
                diaryRepository.addNewRecipe(newRecipeRequest = newRecipeRequest)
            } ?: Resource.Error(resourceProvider.getString(R.string.empty_fields_error))
        }
    }
}