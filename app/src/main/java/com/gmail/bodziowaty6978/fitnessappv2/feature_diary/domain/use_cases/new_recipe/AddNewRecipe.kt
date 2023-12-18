package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.new_recipe

import android.util.Log
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.GetToken
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Ingredient
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class AddNewRecipe(
    private val diaryRepository: DiaryRepository,
    private val getToken: GetToken,
    private val resourceProvider: ResourceProvider
) {
    suspend operator fun invoke(
        ingredients: List<Ingredient>,
        difficulty: String,
        time: String,
        servings: String,
        recipeName: String
    ): Resource<Recipe> {
        val token = getToken()
        token?.let {
            if (ingredients.size < 2) {
                return Resource.Error(resourceProvider.getString(R.string.empty_recipe_ingredients))
            } else if (recipeName.length < 4) {
                Log.e("huj", recipeName)
                return Resource.Error(resourceProvider.getString(R.string.bad_recipe_name))
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
                        name = recipeName
                    )
                    return diaryRepository.addNewRecipe(
                        recipe = recipe,
                        token = token,
                        timestamp = System.currentTimeMillis()
                    )
                } else {
                    return Resource.Error(resourceProvider.getString(R.string.please_make_sure_all_fields_are_filled_in_correctly))
                }
            }
        }
        return Resource.Error(resourceProvider.getUnknownErrorString())
    }
}