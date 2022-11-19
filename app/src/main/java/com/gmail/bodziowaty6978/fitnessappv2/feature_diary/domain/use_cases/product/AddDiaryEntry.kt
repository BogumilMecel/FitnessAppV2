package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.DateModel
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.calculateNutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.DiaryEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class AddDiaryEntry(
    private val diaryRepository: DiaryRepository,
    private val resourceProvider: ResourceProvider,
) {

    suspend operator fun invoke(
        product: Product,
        mealName: String,
        dateModel: DateModel,
        weight: Int?,
    ): Resource<DiaryEntry> {
        return weight?.let {
            if (weight == 0) {
                Resource.Error(
                    uiText = resourceProvider.getString(R.string.incorrect_weight_was_entered)
                )
            } else {
                val diaryEntry = DiaryEntry(
                    product = product,
                    timestamp = dateModel.timestamp,
                    weight = it,
                    mealName = mealName,
                    date = dateModel.date,
                    name = product.name,
                    unit = product.unit,
                    nutritionValues = product.calculateNutritionValues(it)
                )

                diaryRepository.addDiaryEntry(
                    diaryEntry = diaryEntry
                )
            }
        } ?: Resource.Error(
            uiText = resourceProvider.getString(R.string.incorrect_weight_was_entered)
        )
    }
}