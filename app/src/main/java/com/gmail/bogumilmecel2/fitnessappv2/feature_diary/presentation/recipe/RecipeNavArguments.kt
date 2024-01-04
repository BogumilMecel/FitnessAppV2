package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.recipe

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class RecipeNavArguments(val entryData: RecipeEntryData)

@Serializable
sealed class RecipeEntryData(
    open val recipe: Recipe,
    open val mealName: MealName,
    open val date: LocalDate
) {
    @Serializable
    data class Adding(
        @SerialName("meal_name")
        override val mealName: MealName,
        @SerialName("recipe_data")
        override val recipe: Recipe,
        @SerialName("current_date")
        override val date: LocalDate
    ) : RecipeEntryData(
        mealName = mealName,
        recipe = recipe,
        date = date
    )

    @Serializable
    data class Editing(
        @SerialName("recipe_data")
        override val recipe: Recipe,
        @SerialName("meal_name")
        override val mealName: MealName,
        @SerialName("current_date")
        override val date: LocalDate,
        val recipeDiaryEntry: RecipeDiaryEntry,
    ) : RecipeEntryData(
        mealName = mealName,
        recipe = recipe,
        date = date
    )
}
