package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.GetToken
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Meal
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetDiaryEntries(
    private val diaryRepository: DiaryRepository,
    private val sortDiaryEntries: SortDiaryEntries,
    private val resourceProvider: ResourceProvider
) {
    suspend operator fun invoke(
        timestamp: Long,
        mealNames: List<String>
    ): Resource<List<Meal>> {

        val resource = diaryRepository.getDiaryEntries(
            timestamp = timestamp
        )
        return if (resource is Resource.Error) {
            Resource.Error(uiText = resource.uiText ?: resourceProvider.getUnknownErrorString())
        } else {
            withContext(Dispatchers.Default) {
                val data = resource.data!!
                val sortedEntries = sortDiaryEntries(data, mealNames)

                Resource.Success(sortedEntries)
            }
        }
    }
}