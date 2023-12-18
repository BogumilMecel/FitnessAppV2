package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.Serializable

data class ProductNavArguments(
    val entryData: ProductEntryData,
    val product: Product,
    val mealName: MealName
)

@Serializable
sealed class ProductEntryData {
    object Adding: ProductEntryData()
    @Serializable
    data class Editing(val weight: String, val productDiaryEntryId: String): ProductEntryData()

    companion object {
        val serializer = PolymorphicSerializer(ProductEntryData::class)
    }
}

