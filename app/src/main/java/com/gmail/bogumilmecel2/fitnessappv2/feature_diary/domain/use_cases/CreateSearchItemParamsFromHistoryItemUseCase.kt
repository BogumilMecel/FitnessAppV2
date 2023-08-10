package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductDiaryHistoryItem
import com.gmail.bogumilmecel2.ui.components.complex.SearchItemParams

class CreateSearchItemParamsFromHistoryItemUseCase(private val resourceProvider: ResourceProvider) {
    operator fun invoke(
        productDiaryHistoryItem: ProductDiaryHistoryItem,
        onClick: () -> Unit,
        onLongClick: () -> Unit
    ): SearchItemParams = with(productDiaryHistoryItem) {
        return SearchItemParams(
            name = productName,
            textBelowName = resourceProvider.getString(
                measurementUnit.getStringResWithValue(),
                weight
            ),
            endText = resourceProvider.getString(
                R.string.kcal_with_value,
                nutritionValues.calories
            ),
            onItemClick = onClick,
            onItemLongClick = onLongClick
        )
    }
}