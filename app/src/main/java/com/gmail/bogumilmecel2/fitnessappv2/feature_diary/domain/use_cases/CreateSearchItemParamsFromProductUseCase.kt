package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.ui.components.complex.SearchItemParams

class CreateSearchItemParamsFromProductUseCase(private val resourceProvider: ResourceProvider) {
    operator fun invoke(
        product: Product,
        onClick: () -> Unit,
        onLongClick: () -> Unit
    ): SearchItemParams {
        return SearchItemParams(
            name = product.name,
            textBelowName = resourceProvider.getString(R.string.measurement_unit_gram_with_value, "100"),
            endText = resourceProvider.getString(R.string.kcal_with_value, product.nutritionValues.calories),
            onItemClick = onClick,
            onItemLongClick = onLongClick
        )
    }
}