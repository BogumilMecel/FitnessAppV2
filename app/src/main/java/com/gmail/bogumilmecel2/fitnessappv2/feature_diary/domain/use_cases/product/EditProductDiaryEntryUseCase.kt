package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.EditProductDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class EditProductDiaryEntryUseCase(
    private val resourceProvider: ResourceProvider,
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(
        productDiaryEntry: ProductDiaryEntry,
        newWeightStringValue: String
    ): Resource<Unit> {
        return newWeightStringValue.toValidInt()?.let { weight ->
            diaryRepository.editProductDiaryEntry(
                editProductDiaryEntryRequest = EditProductDiaryEntryRequest(
                    productDiaryEntry = productDiaryEntry,
                    newWeight = weight
                )
            )
        } ?: Resource.Error(uiText = resourceProvider.getUnknownErrorString())
    }
}