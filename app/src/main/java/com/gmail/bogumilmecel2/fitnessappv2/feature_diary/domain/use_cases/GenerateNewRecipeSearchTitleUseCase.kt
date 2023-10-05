package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider

class GenerateNewRecipeSearchTitleUseCase(private val resourceProvider: ResourceProvider) {
    operator fun invoke(recipeName: String) = recipeName
        .ifEmpty { resourceProvider.getString(stringResId = R.string.search_add_ingredient) }
}