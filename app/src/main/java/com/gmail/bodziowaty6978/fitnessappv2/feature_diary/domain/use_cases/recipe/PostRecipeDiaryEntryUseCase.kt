package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.recipe

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.DateModel
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntryRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class PostRecipeDiaryEntryUseCase(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(
        recipe: Recipe,
        servingsString: String,
        dateModel: DateModel,
        mealName: String
    ): Resource<Unit> {
        val servingsValue = servingsString.toIntOrNull()

        return if (servingsValue == null) {
            Resource.Error(uiText = "")
        } else {
            diaryRepository.addRecipeDiaryEntry(
                recipeDiaryEntryRequest = RecipeDiaryEntryRequest(
                    recipe = recipe,
                    servings = servingsValue,
                    date = dateModel.date,
                    timestamp = dateModel.timestamp,
                    mealName = mealName
                )
            )
        }
    }
}