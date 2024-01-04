package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository
import kotlinx.datetime.LocalDate

class PostRecipeDiaryEntryUseCase(
    private val diaryRepository: DiaryRepository,
    private val offlineDiaryRepository: OfflineDiaryRepository,
    private val resourceProvider: ResourceProvider,
    private val calculateRecipeNutritionValuesForServingsUseCase: CalculateRecipeNutritionValuesForServingsUseCase
) {
    suspend operator fun invoke(
        recipe: Recipe,
        servingsString: String,
        date: LocalDate,
        mealName: MealName
    ): Resource<Unit> {
        val servings = servingsString.toValidInt() ?: return getServingsResourceError()
        if (servings <= 0) return getServingsResourceError()

        val insertedRecipeDiaryEntry = diaryRepository.insertRecipeDiaryEntry(
            recipeDiaryEntry = RecipeDiaryEntry(
                recipeId = recipe.id,
                servings = servings,
                date = date,
                mealName = mealName,
                nutritionValues = calculateRecipeNutritionValuesForServingsUseCase(
                    recipe = recipe,
                    servings = servings
                )
            )
        ).data ?: return Resource.Error()

        offlineDiaryRepository.insertRecipe(recipe)

        return offlineDiaryRepository.insertRecipeDiaryEntry(recipeDiaryEntry = insertedRecipeDiaryEntry)
    }

    private fun getServingsResourceError() = Resource.Error<Unit>(uiText = resourceProvider.getString(R.string.recipe_servings_error))
}