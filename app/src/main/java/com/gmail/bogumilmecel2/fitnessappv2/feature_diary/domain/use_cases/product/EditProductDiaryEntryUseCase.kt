package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.RealResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.EditProductDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class EditProductDiaryEntryUseCase(
    private val realResourceProvider: RealResourceProvider,
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(
        productDiaryEntry: ProductDiaryEntry,
        newWeightStringValue: String
    ): Resource<Unit> {
        val weight = newWeightStringValue.toValidInt() ?: return Resource.Error(uiText = realResourceProvider.getUnknownErrorString())

        if (weight <= 0) {
            return Resource.Error(uiText = realResourceProvider.getUnknownErrorString())
        }

        return diaryRepository.editProductDiaryEntry(
            editProductDiaryEntryRequest = EditProductDiaryEntryRequest(
                productDiaryEntryId = productDiaryEntry.id,
                newWeight = weight
            )
        )
    }
}