package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class ProductNavArguments(val entryData: ProductEntryData)

@Serializable
sealed class ProductEntryData(
    open val product: Product,
    open val mealName: MealName,
    open val date: String
) {
    @Serializable
    data class Adding(
        @SerialName("meal_name")
        override val mealName: MealName,
        @SerialName("product_data")
        override val product: Product,
        @SerialName("display_date")
        override val date: String
    ) : ProductEntryData(
        mealName = mealName,
        product = product,
        date = date
    )

    @Serializable
    data class Editing(
        val productDiaryEntry: ProductDiaryEntry,
        @SerialName("display_date")
        override val date: String
    ) : ProductEntryData(
        mealName = productDiaryEntry.mealName,
        product = productDiaryEntry.product,
        date = date
    )

    companion object {
        val serializer = PolymorphicSerializer(ProductEntryData::class)
    }
}

