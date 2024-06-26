package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

data class SearchNavArguments(val entryData: SearchEntryData)

@Serializable
sealed interface SearchEntryData {
    @Serializable
    data class DiaryArguments(
        val mealName: MealName,
        val date: LocalDate
    ) : SearchEntryData

    @Serializable
    data class NewRecipeArguments(val recipeName: String) : SearchEntryData
}