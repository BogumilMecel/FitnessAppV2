package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DateModel
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class PostRecipeDiaryEntryUseCase(
    private val diaryRepository: DiaryRepository,
    private val resourceProvider: ResourceProvider
) {
    suspend operator fun invoke(
        recipe: Recipe,
        servingsString: String,
        dateModel: DateModel,
        mealName: MealName
    ): Resource<Unit> {
        val servingsValue = servingsString.toValidInt()

        return if (servingsValue == null || servingsValue <= 0) {
            Resource.Error(uiText = resourceProvider.getString(R.string.recipe_servings_error))
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