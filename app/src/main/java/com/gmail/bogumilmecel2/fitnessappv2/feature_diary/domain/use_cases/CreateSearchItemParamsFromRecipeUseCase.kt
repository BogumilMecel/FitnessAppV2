package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.multiplyBy
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens.SearchItemParams

class CreateSearchItemParamsFromRecipeUseCase(private val resourceProvider: ResourceProvider) {
    operator fun invoke(
        recipe: Recipe,
        onClick: () -> Unit,
        onLongClick: () -> Unit
    ) = SearchItemParams(
        name = recipe.name,
        textBelowName = resourceProvider.getPluralString(
            pluralResId = R.plurals.servings,
            quantity = recipe.servings
        ),
        endText = resourceProvider.getString(
            stringResId = R.string.kcal_with_value,
            recipe.nutritionValues.multiplyBy(
                number = (1.0 / recipe.servings.toDouble())
            ).calories
        ),
        onItemClick = onClick,
        onItemLongClick = onLongClick
    )
}