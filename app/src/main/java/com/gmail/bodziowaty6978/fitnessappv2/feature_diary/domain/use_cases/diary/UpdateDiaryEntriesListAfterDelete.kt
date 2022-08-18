package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Meal

class UpdateDiaryEntriesListAfterDelete {

    operator fun invoke(
        diaryEntryId: String,
        meals: List<Meal>
    ): List<Meal> {
        meals.forEach { meal ->
            meal.diaryEntries.forEach { diaryEntryWithId ->
                if (diaryEntryWithId.id == diaryEntryId) {
                    val mutableDiaryEntries = meal.diaryEntries.toMutableList()
                    mutableDiaryEntries.removeIf { comparedDiaryEntryWithId ->
                        comparedDiaryEntryWithId.id == diaryEntryId
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