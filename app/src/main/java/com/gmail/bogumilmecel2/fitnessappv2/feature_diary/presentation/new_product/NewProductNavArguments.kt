package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_product

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

data class NewProductNavArguments(
    val entryData: NewProductEntryData,
    val barcode: String?,
)

@Serializable
sealed interface NewProductEntryData {
    @Serializable
    data class SearchArguments(
        val mealName: MealName,
        val date: LocalDate
    ) : NewProductEntryData

    @Serializable
    data class NewRecipeArguments(
        val recipeName: String
    ) : NewProductEntryData
}