package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.EditRecipeDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class EditRecipeDiaryEntryUseCase(
    private val resourceProvider: ResourceProvider,
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(
        recipeDiaryEntry: RecipeDiaryEntry,
        newServingsStringValue: String
    ): Resource<Unit> {
        val servings = newServingsStringValue.toValidInt()
            ?: return Resource.Error(uiText = resourceProvider.getUnknownErrorString())

        if (servings <= 0) {
            return Resource.Error(uiText = resourceProvider.getUnknownErrorString())
        }

        if (servings == recipeDiaryEntry.servings) {
            return Resource.Error(uiText = resourceProvider.getUnknownErrorString())
        }

        return diaryRepository.editRecipeDiaryEntry(
            editRecipeDiaryEntryRequest = EditRecipeDiaryEntryRequest(
                recipeDiaryEntryId = recipeDiaryEntry.id,
                newServings = servings
            )
        )
    }
}