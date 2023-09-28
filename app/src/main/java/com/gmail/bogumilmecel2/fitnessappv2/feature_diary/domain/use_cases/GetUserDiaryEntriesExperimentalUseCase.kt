package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class GetUserDiaryEntriesExperimentalUseCase(private val diaryRepository: DiaryRepository) {
    suspend operator fun invoke(): Resource<Unit> {
        val diaryEntriesCount = diaryRepository.getDiaryEntriesCount().data ?: return Resource.Error()

        if (diaryEntriesCount <= 0) {
            diaryRepository.getDiaryEntriesComplete().data?.let {
                diaryRepository.insertOfflineDiaryEntries(diaryEntriesResponse = it)
            }
        }

        return Resource.Success(Unit)
    }
}