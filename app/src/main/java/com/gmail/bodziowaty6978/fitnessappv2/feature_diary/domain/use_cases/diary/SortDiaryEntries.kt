package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.DiaryEntriesResponse
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.MealName

class SortDiaryEntries {

    operator fun invoke(
        diaryEntriesResponse: DiaryEntriesResponse
    ):Map<MealName,List<DiaryItem>>{
        return mutableListOf<DiaryItem>().run {
            addAll(diaryEntriesResponse.recipeDiaryEntries)
            addAll(diaryEntriesResponse.productDiaryEntries)
            sortByDescending { it.timestamp }
            groupBy { it.mealName }
        }
    }
}