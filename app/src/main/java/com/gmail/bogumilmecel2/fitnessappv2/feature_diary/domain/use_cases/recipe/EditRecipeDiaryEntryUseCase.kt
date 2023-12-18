package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.EditRecipeDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class EditRecipeDiaryEntryUseCase(private val diaryRepository: DiaryRepository) {
    suspend operator fun invoke(
        recipeDiaryEntryId: String,
        newServingsStringValue: String,
        originalServingsStringValue: String
    ): Resource<Unit> {
        val servings = newServingsStringValue.toValidInt() ?: return Resource.Error()
        val originalServings = originalServingsStringValue.toValidInt() ?: return Resource.Error()

        if (servings <= 0) return Resource.Error()
        if (servings == originalServings) return Resource.Error()

        return diaryRepository.editRecipeDiaryEntry(
            editRecipeDiaryEntryRequest = EditRecipeDiaryEntryRequest(
                recipeDiaryEntryId = recipeDiaryEntryId,
                newServings = servings
            )
        )
    }
}