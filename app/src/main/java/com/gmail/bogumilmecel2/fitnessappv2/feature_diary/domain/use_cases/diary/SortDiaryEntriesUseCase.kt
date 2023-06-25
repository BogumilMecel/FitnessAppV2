package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName

class SortDiaryEntriesUseCase {

    operator fun invoke(
        diaryEntries: List<DiaryItem>
    ):Map<MealName,List<DiaryItem>>{
        return diaryEntries.toMutableList().run {
            sortByDescending { it.utcTimestamp }
            groupBy { it.mealName }
        }
    }
}