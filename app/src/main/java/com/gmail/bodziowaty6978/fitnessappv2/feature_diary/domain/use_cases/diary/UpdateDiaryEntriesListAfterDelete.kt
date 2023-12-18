package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Meal

class UpdateDiaryEntriesListAfterDelete {

    operator fun invoke(
        diaryEntryId: Int,
        meals: List<Meal>
    ): List<Meal> {
        meals.forEachIndexed { index, meal ->
            val diaryEntry = meal.diaryEntries.find {
                it.diaryEntry.id == diaryEntryId
            }

            diaryEntry?.let {
                val mutableDiaryEntries = meal.diaryEntries.toMutableList()
                mutableDiaryEntries.remove(it)

                val newMeal = Meal(
                    mealName = meal.mealName,
                    diaryEntries = mutableDiaryEntries
                )

                val mutableMeals = meals.toMutableList()
                mutableMeals[index] = newMeal
                return mutableMeals
            }
        }
        return meals
    }
}