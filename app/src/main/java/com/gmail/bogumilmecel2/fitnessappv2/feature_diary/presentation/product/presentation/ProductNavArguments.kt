package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DateTransferObject
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
    open val dateTransferObject: DateTransferObject
) {
    @Serializable
    data class Adding(
        @SerialName("meal_name")
        override val mealName: MealName,
        @SerialName("product_data")
        override val product: Product,
        @SerialName("display_date")
        override val dateTransferObject: DateTransferObject
    ) : ProductEntryData(
        mealName = mealName,
        product = product,
        dateTransferObject = dateTransferObject
    )

    @Serializable
    data class Editing(
        @SerialName("meal_name")
        override val mealName: MealName,
        @SerialName("product_data")
        override val product: Product,
        val productDiaryEntry: ProductDiaryEntry,
        val date: String,
        val displayedDate: String,
    ) : ProductEntryData(
        mealName = mealName,
        product = product,
        dateTransferObject = DateTransferObject(
            displayedDate = displayedDate,
            realDate = date
        )
    )

    companion object {
        val serializer = PolymorphicSerializer(ProductEntryData::class)
    }
}

