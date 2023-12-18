package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Ingredient
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens.SearchItemParams

class CreateSearchItemParamsFromIngredientUseCase(private val resourceProvider: ResourceProvider) {
    operator fun invoke(
        ingredient: Ingredient,
        onClick: () -> Unit,
        onLongClick: () -> Unit
    ) = SearchItemParams(
        name = ingredient.productName,
        endText = resourceProvider.getString(R.string.kcal_with_value, ingredient.nutritionValues.calories.toString()),
        textBelowName = resourceProvider.getString(R.string.measurement_unit_gram_with_value, ingredient.weight.toString()),
        onItemClick = onClick,
        onItemLongClick = onLongClick
    )
}