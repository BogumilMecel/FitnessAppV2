package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository

class EditRecipeDiaryEntryUseCase(
    private val diaryRepository: DiaryRepository,
    private val offlineDiaryRepository: OfflineDiaryRepository,
    private val calculateRecipeNutritionValuesForServingsUseCase: CalculateRecipeNutritionValuesForServingsUseCase
) {
    suspend operator fun invoke(
        recipe: Recipe,
        recipeDiaryEntry: RecipeDiaryEntry,
        newServingsStringValue: String,
    ): Resource<Unit> {
        val servings = newServingsStringValue.toValidInt() ?: return Resource.Error()

        if (servings <= 0) return Resource.Error()
        if (servings == recipeDiaryEntry.servings) return Resource.Error()

        val newNutritionValues = calculateRecipeNutritionValuesForServingsUseCase(
            recipe = recipe,
            servings = servings
        )

        val resource = diaryRepository.editRecipeDiaryEntry(
            recipeDiaryEntry = recipeDiaryEntry.copy(
                nutritionValues = newNutritionValues,
                servings = servings
            )
        )

        return when(resource) {
            is Resource.Error -> {
                Resource.Error()
            }

            is Resource.Success -> {
                offlineDiaryRepository.insertRecipeDiaryEntry(recipeDiaryEntry = resource.data)
            }
        }
    }
}