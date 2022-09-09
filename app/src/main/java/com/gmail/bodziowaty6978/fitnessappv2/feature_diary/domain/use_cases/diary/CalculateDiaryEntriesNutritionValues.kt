package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.CalculatedDiaryEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.DiaryEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.calculateNutritionValues

class CalculateDiaryEntriesNutritionValues {

    operator fun invoke(diaryEntries:List<DiaryEntry>):List<CalculatedDiaryEntry>{
        return diaryEntries.map {
            it.calculateNutritionValues()
        }
    }
}