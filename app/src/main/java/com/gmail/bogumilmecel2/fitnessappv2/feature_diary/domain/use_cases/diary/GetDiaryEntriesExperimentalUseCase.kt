package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class GetDiaryEntriesExperimentalUseCase(private val diaryRepository: DiaryRepository) {
    suspend operator fun invoke() {
        val diaryEntries = diaryRepository.getDiaryEntries(
            latestProductDiaryEntryTimestamp = diaryRepository.getLatestProductDiaryEntry().data?.lastEditedUtcTimestamp,
            latestRecipeDiaryEntryTimestamp = diaryRepository.getLatestRecipeDiaryEntry().data?.lastEditedUtcTimestamp
        )
    }
}