package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class GetOnlineDiaryEntriesUseCase(private val diaryRepository: DiaryRepository) {
    suspend operator fun invoke(date: String): Resource<Unit> {
        val diaryEntries = diaryRepository.getDiaryEntries(date = date).data ?: return Resource.Error()
        diaryRepository.deleteOfflineDiaryEntries(
            date = date,
            productDiaryEntriesIds = diaryEntries.productDiaryEntries.map { it.id },
            recipeDiaryEntriesIds = diaryEntries.recipeDiaryEntries.map { it.id }
        ).data ?: return Resource.Error()

        return diaryRepository.insertOfflineDiaryEntries(diaryEntries)
    }
}