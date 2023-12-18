package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Meal

class UpdateDiaryEntriesListAfterDelete {

    operator fun invoke(
        diaryEntryId: Int,
        meals: List<Meal>
    ): List<Meal> {
        meals.forEach { meal ->
            meal.diaryEntries.forEach { calculatedDiaryEntry ->
                val diaryEntry = calculatedDiaryEntry.diaryEntry
                if (diaryEntry.id == diaryEntryId) {
                    val mutableDiaryEntries = meal.diaryEntries.toMutableList()
                    mutableDiaryEntries.removeIf { comparedDiaryEntryWithId ->
                        comparedDiaryEntryWithId.diaryEntry.id == diaryEntryId
                    }
                    val mutableMeals = meals.toMutableList()
                    mutableMeals.remove(meal)
                    mutableMeals.add(
                        Meal(
                            mealName = meal.mealName,
                            diaryEntries = mutableDiaryEntries
                        )
                    )
                    return mutableMeals
                }
            }
        }
        return meals
    }
}