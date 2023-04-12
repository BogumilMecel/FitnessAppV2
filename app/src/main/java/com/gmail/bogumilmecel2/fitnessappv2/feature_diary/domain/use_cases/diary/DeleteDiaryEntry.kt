package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DeleteDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class DeleteDiaryEntry(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(diaryItem: DiaryItem): Resource<Unit> {
        return diaryRepository.deleteDiaryEntry(
            deleteDiaryEntryRequest = DeleteDiaryEntryRequest(
                diaryEntryId = diaryItem.id,
                diaryEntryType = diaryItem.getDiaryEntryType()
            ),
        )
    }
}