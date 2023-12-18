package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Meal
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry

class SortDiaryEntries {

    operator fun invoke(
        entries:List<ProductDiaryEntry>,
        mealNames:List<String>
    ):List<Meal>{
        val meals = mutableListOf<Meal>()
        mealNames.forEach { mealName ->
            val entriesList = entries.filter { it.mealName == mealName }
            meals.add(
                Meal(
                    mealName=mealName,
                    diaryEntries = entriesList
                )
            )
        }
        return meals
    }
}