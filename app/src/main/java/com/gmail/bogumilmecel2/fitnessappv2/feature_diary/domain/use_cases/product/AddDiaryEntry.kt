package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DateModel
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntryPostRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class AddDiaryEntry(
    private val diaryRepository: DiaryRepository,
    private val resourceProvider: ResourceProvider,
) {

    suspend operator fun invoke(
        product: Product,
        mealName: MealName,
        dateModel: DateModel,
        weight: Int?,
    ): Resource<ProductDiaryEntry> {
        return weight?.let {
            if (weight == 0) {
                Resource.Error(
                    uiText = resourceProvider.getString(R.string.incorrect_weight_was_entered)
                )
            } else {
                val productDiaryEntryPostRequest = ProductDiaryEntryPostRequest(
                    product = product,
                    timestamp = dateModel.timestamp,
                    weight = it,
                    mealName = mealName,
                    date = dateModel.date,
                )

                diaryRepository.addProductDiaryEntry(
                    productDiaryEntryPostRequest = productDiaryEntryPostRequest
                )
            }
        } ?: Resource.Error(
            uiText = resourceProvider.getString(R.string.incorrect_weight_was_entered)
        )
    }
}