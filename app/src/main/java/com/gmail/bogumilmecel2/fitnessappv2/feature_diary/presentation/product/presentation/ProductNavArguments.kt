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
sealed class ProductEntryData(open val product: Product) {
    @Serializable
    data class Adding(
        @SerialName("product_data")
        override val product: Product,
        val mealName: MealName,
        val dateTransferObject: DateTransferObject
    ) : ProductEntryData(product = product)

    @Serializable
    data class Editing(
        @SerialName("product_data")
        override val product: Product,
        val mealName: MealName,
        val productDiaryEntry: ProductDiaryEntry,
        val date: String,
        val displayedDate: String,
    ) : ProductEntryData(product = product)

    @Serializable
    data class NewRecipe(
        @SerialName("product_data")
        override val product: Product,
        val recipeName: String
    ) : ProductEntryData(product = product)

    companion object {
        val serializer = PolymorphicSerializer(ProductEntryData::class)
    }
}

