package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.product

import androidx.compose.ui.tooling.data.EmptyGroup.name
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton.CurrentDate
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.DateModel
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.GetToken
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.DiaryEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class AddDiaryEntry(
    private val diaryRepository: DiaryRepository,
    private val resourceProvider: ResourceProvider,
    private val getToken: GetToken
) {

    suspend operator fun invoke(
        product: Product,
        mealName: String,
        dateModel: DateModel,
        weight: Int?,
        nutritionValues: NutritionValues,
    ): Resource<DiaryEntry> {
        return weight?.let {
            if (weight==0){
                Resource.Error(
                    uiText = resourceProvider.getString(R.string.incorrect_weight_was_entered)
                )
            }else{
                val diaryEntry = DiaryEntry(
                 product = product,
                 timeStamp = dateModel.timestamp,
                 weight = it,
                 mealName = mealName
                )

                val token = getToken()
                if (token is Resource.)

                diaryRepository.addDiaryEntry(
                    diaryEntry = diaryEntry,
                    token = getToken()
                )
            }
        } ?: Resource.Error(
            uiText = resourceProvider.getString(R.string.incorrect_weight_was_entered)
        )
    }
}