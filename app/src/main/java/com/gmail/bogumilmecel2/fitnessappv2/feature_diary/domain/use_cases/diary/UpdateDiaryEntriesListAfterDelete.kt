package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem

class UpdateDiaryEntriesListAfterDelete {

    operator fun invoke(
        diaryEntry: DiaryItem,
        diaryEntries: List<DiaryItem>,
    ): List<DiaryItem> {
        val mutableList = diaryEntries.toMutableList()
        mutableList.remove(diaryEntry)
        return mutableList
    }
}