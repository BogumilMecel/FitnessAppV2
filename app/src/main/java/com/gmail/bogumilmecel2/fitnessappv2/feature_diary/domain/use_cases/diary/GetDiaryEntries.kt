package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class GetDiaryEntries(
    private val diaryRepository: DiaryRepository,
    private val sortDiaryEntries: SortDiaryEntries
) {
    suspend operator fun invoke(
        date: String,
    ): Resource<Map<MealName, List<DiaryItem>>> {
        return when (val resource = diaryRepository.getDiaryEntries(date = date)) {
            is Resource.Success -> {
                Resource.Success(
                    data = sortDiaryEntries(
                        diaryEntriesResponse = resource.data
                    )
                )
            }

            is Resource.Error -> Resource.Error(uiText = resource.uiText)
        }
    }
}