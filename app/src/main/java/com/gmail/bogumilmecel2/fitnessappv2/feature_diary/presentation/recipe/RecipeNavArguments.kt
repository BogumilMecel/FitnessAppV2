package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.recipe

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DateTransferObject
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class RecipeNavArguments(
    val entryData: RecipeEntryData,
)

@Serializable
sealed class RecipeEntryData(
    open val recipe: Recipe,
    open val mealName: MealName,
    open val dateTransferObject: DateTransferObject
) {
    @Serializable
    data class Adding(
        @SerialName("meal_name")
        override val mealName: MealName,
        @SerialName("recipe_data")
        override val recipe: Recipe,
        @SerialName("current_date")
        override val dateTransferObject: DateTransferObject
    ) : RecipeEntryData(
        mealName = mealName,
        recipe = recipe,
        dateTransferObject = dateTransferObject
    )

    @Serializable
    data class Editing(
        @SerialName("recipe_data")
        override val recipe: Recipe,
        @SerialName("meal_name")
        override val mealName: MealName,
        val date: String,
        val displayedDate: String,
        val servings: String,
        val recipeDiaryEntryId: String
    ) : RecipeEntryData(
        mealName = mealName,
        recipe = recipe,
        dateTransferObject = DateTransferObject(
            displayedDate = displayedDate,
            realDate = date
        )
    )
}
