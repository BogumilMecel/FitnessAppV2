package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class PostRecipeDiaryEntryUseCase(
    private val diaryRepository: DiaryRepository,
    private val resourceProvider: ResourceProvider
) {
    suspend operator fun invoke(
        recipeId: String,
        servingsString: String,
        date: String,
        mealName: MealName
    ): Resource<Unit> {
        val servingsValue = servingsString.toValidInt() ?: return getServingsResourceError()
        if (servingsValue <= 0) return getServingsResourceError()

        val insertedRecipeDiaryEntry = diaryRepository.insertRecipeDiaryEntry(
            recipeDiaryEntryRequest = RecipeDiaryEntryRequest(
                recipeId = recipeId,
                servings = servingsValue,
                date = date,
                mealName = mealName
            )
        ).data ?: return Resource.Error()

        return diaryRepository.insertOfflineDiaryEntry(insertedRecipeDiaryEntry)
    }

    private fun getServingsResourceError() = Resource.Error<Unit>(uiText = resourceProvider.getString(R.string.recipe_servings_error))
}