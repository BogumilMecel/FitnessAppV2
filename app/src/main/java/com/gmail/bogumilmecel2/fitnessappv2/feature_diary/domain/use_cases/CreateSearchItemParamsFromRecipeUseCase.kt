package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.multiplyBy
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.let2
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.ui.components.complex.SearchItemParams

class CreateSearchItemParamsFromRecipeUseCase(private val resourceProvider: ResourceProvider) {
    operator fun invoke(
        recipe: Recipe,
        onClick: () -> Unit,
        onLongClick: () -> Unit
    ) = SearchItemParams(
        name = recipe.name.orEmpty(),
        textBelowName = recipe.servings?.let {
            resourceProvider.getPluralString(
                pluralResId = R.plurals.servings,
                quantity = it
            )
        }.orEmpty(),
        endText = let2(recipe.servings, recipe.nutritionValues) { servings, nutritionValues ->
            resourceProvider.getString(
                stringResId = R.string.kcal_with_value,
                nutritionValues.multiplyBy(
                    number = (1.0 / servings.toDouble())
                ).calories
            )
        }.orEmpty(),
        onItemClick = onClick,
        onItemLongClick = onLongClick
    )
}