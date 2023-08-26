package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class GetUserDiaryEntriesExperimentalUseCase(private val diaryRepository: DiaryRepository) {
    suspend operator fun invoke(): Resource<Unit> {
        val diaryEntriesCount = diaryRepository.getDiaryEntriesCount().data ?: return Resource.Error()

        val diaryEntriesResponse = if (diaryEntriesCount <= 0) {
            diaryRepository.getDiaryEntriesComplete()
        } else {
            diaryRepository.getDiaryEntriesExperimental()
        }.data ?: return Resource.Error()

        return diaryRepository.insertOfflineDiaryEntries(diaryEntriesResponse)
    }
}