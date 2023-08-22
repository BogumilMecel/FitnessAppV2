package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary

import android.util.Log
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.TAG
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class GetDiaryEntriesUseCase(
    private val diaryRepository: DiaryRepository,
    private val sortDiaryEntriesUseCase: SortDiaryEntriesUseCase,
    private val getDiaryEntriesListFromResponseUseCase: GetDiaryEntriesListFromResponseUseCase
) {
    suspend operator fun invoke(
        date: String,
    ): Resource<Map<MealName, List<DiaryItem>>> {
        return when (val resource = diaryRepository.getDiaryEntries(date = date)) {
            is Resource.Success -> {
                Log.e(TAG, resource.data.toString())
                Resource.Success(
                    data = sortDiaryEntriesUseCase(
                        diaryEntries = getDiaryEntriesListFromResponseUseCase(diaryEntriesResponse = resource.data)
                    )
                )
            }

            is Resource.Error -> Resource.Error(uiText = resource.uiText)
        }
    }
}