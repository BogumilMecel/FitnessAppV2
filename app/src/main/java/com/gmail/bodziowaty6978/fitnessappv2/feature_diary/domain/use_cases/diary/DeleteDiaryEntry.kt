package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class DeleteDiaryEntry(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(diaryEntryId: String): Resource<Unit> {
        return diaryRepository.deleteDiaryEntry(
            diaryEntryId = diaryEntryId,
        )
    }
}