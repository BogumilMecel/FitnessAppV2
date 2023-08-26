package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class GetDiaryEntriesUseCase(private val diaryRepository: DiaryRepository) {
    suspend operator fun invoke(date: String): Resource<List<DiaryItem>> {
        return when (val resource = diaryRepository.getOfflineDiaryEntries(date = date)) {
            is Resource.Success -> {
                Resource.Success(
                    data = buildList {
                        addAll(resource.data.recipeDiaryEntries)
                        addAll(resource.data.productDiaryEntries)
                    }
                )
            }

            is Resource.Error -> Resource.Error(uiText = resource.uiText)
        }
    }
}