package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntriesResponse

class GetDiaryEntriesListFromResponseUseCase {
    operator fun invoke(diaryEntriesResponse: DiaryEntriesResponse): List<DiaryItem> {
        return mutableListOf<DiaryItem>().run {
            addAll(diaryEntriesResponse.productDiaryEntries)
            addAll(diaryEntriesResponse.recipeDiaryEntries)
            toList()
        }
    }
}