package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Meal
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.DiaryEntry

class SortDiaryEntries(){

    operator fun invoke(
        entries:List<DiaryEntry>,
        mealNames:List<String>
    ):List<Meal>{
        val meals = mutableListOf<Meal>()
        mealNames.forEach { mealName ->
            val entriesList = mutableListOf<DiaryEntry>()
            entries.forEach { diaryEntry ->
                if (diaryEntry.mealName==mealName){
                    entriesList.add(diaryEntry)
                }
            }
            meals.add(Meal(mealName=mealName, diaryEntries = entriesList))

        }
        return meals
    }
}