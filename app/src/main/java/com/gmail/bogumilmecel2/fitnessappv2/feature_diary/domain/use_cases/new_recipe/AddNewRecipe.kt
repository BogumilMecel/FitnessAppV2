package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_recipe

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.RealResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Ingredient
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.NewRecipeRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.utils.Difficulty
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.utils.TimeRequired
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class AddNewRecipe(
    private val diaryRepository: DiaryRepository,
    private val realResourceProvider: RealResourceProvider
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
            Resource.Error(realResourceProvider.getString(R.string.empty_recipe_ingredients))
        } else if (recipeName.length < 4) {
            Resource.Error(realResourceProvider.getString(R.string.bad_recipe_name))
        } else {
            servings.toIntOrNull()?.let { servingsValue ->
                val newRecipeRequest = NewRecipeRequest(
                    ingredients = ingredients,
                    timeRequired = time,
                    difficulty = difficulty,
                    servings = servingsValue,
                    timestamp = System.currentTimeMillis(),
                    recipeName = recipeName,
                    isPublic = isRecipePublic
                )
                diaryRepository.addNewRecipe(newRecipeRequest = newRecipeRequest)
            } ?: Resource.Error(realResourceProvider.getString(R.string.please_make_sure_all_fields_are_filled_in_correctly))
        }
    }
}