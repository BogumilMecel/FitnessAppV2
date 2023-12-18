package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.GetUserCurrencyUseCase
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.RecipePriceResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Ingredient
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class GetRecipePriceFromIngredientsUseCase(
    private val diaryRepository: DiaryRepository,
    private val getUserCurrencyUseCase: GetUserCurrencyUseCase
) {
    suspend operator fun invoke(
        ingredients: List<Ingredient>
    ): Resource<RecipePriceResponse?> {
        return diaryRepository.getRecipePriceFromIngredients(
            recipePriceRequest = RecipePriceRequest(
                ingredients = ingredients,
            ),
            currency = getUserCurrencyUseCase()
        )
    }
}