package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class GetRecipeUseCase(private val diaryRepository: DiaryRepository) {

    suspend operator fun invoke(recipeId: String): Resource<Recipe?> {
        val cachedRecipe = diaryRepository.getOfflineRecipe(recipeId = recipeId).data

        if (cachedRecipe != null) {
            return Resource.Success(cachedRecipe)
        }

        return diaryRepository.getRecipe(recipeId)
    }
}