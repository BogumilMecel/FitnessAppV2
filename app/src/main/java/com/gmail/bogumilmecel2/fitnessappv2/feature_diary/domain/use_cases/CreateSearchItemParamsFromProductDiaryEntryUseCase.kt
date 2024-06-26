package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.let2
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.ui.components.complex.SearchItemParams

class CreateSearchItemParamsFromProductDiaryEntryUseCase(private val resourceProvider: ResourceProvider) {
    operator fun invoke(
        productDiaryEntry: ProductDiaryEntry,
        onClick: () -> Unit,
        onLongClick: () -> Unit
    ): SearchItemParams = with(productDiaryEntry) {
        return SearchItemParams(
            name = productName.orEmpty(),
            textBelowName = let2(weight, productMeasurementUnit) { weight, productMeasurementUnit ->
                resourceProvider.getString(
                    productMeasurementUnit.getStringResWithValue(),
                    weight
                )
            }.orEmpty(),
            endText = nutritionValues?.calories?.let {
                resourceProvider.getString(
                    R.string.kcal_with_value,
                    nutritionValues.calories
                )
            }.orEmpty(),
            onItemClick = onClick,
            onItemLongClick = onLongClick
        )
    }
}