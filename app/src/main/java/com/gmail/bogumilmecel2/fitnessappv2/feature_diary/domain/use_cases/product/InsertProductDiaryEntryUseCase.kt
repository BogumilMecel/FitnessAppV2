package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntryPostRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class InsertProductDiaryEntryUseCase(
    private val diaryRepository: DiaryRepository,
    private val resourceProvider: ResourceProvider,
) {

    suspend operator fun invoke(
        productId: String,
        mealName: MealName,
        date: String,
        weightStringValue: String,
    ): Resource<Unit> {
        val weight = weightStringValue.toValidInt() ?: return getWeightErrorResource()
        if (weight <= 0) return getWeightErrorResource()

        return diaryRepository.insertProductDiaryEntry(
            productDiaryEntryPostRequest = ProductDiaryEntryPostRequest(
                productId = productId,
                weight = weight,
                mealName = mealName,
                date = date,
            )
        )
    }

    private fun getWeightErrorResource() =
        Resource.Error<Unit>(uiText = resourceProvider.getString(R.string.incorrect_weight_was_entered))
}