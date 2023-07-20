package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.EditProductDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class EditProductDiaryEntryUseCase(private val diaryRepository: DiaryRepository) {
    suspend operator fun invoke(
        productDiaryEntryId: String,
        newWeightStringValue: String,
    ): Resource<Unit> {
        val weight = newWeightStringValue.toValidInt() ?: return Resource.Error()

        if (weight <= 0) return Resource.Error()

        return diaryRepository.editProductDiaryEntry(
            editProductDiaryEntryRequest = EditProductDiaryEntryRequest(
                productDiaryEntryId = productDiaryEntryId,
                newWeight = weight
            )
        )
    }
}