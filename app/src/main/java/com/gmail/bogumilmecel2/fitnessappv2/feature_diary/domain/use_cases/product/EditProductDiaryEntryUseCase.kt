package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository

class EditProductDiaryEntryUseCase(
    private val diaryRepository: DiaryRepository,
    private val offlineDiaryRepository: OfflineDiaryRepository,
    private val calculateProductNutritionValuesUseCase: CalculateProductNutritionValuesUseCase
) {
    suspend operator fun invoke(
        product: Product,
        productDiaryEntry: ProductDiaryEntry,
        newWeightStringValue: String,
    ): Resource<Unit> {
        val weight = newWeightStringValue.toValidInt() ?: return Resource.Error()

        if (weight <= 0) return Resource.Error()
        if (weight == productDiaryEntry.weight) return Resource.Error()

        val newNutritionValues = calculateProductNutritionValuesUseCase(
            product = product,
            weight = weight
        )

        val resource = diaryRepository.editProductDiaryEntry(
            productDiaryEntry = productDiaryEntry.copy(
                weight = weight,
                nutritionValues = newNutritionValues
            )
        )

        return when(resource) {
            is Resource.Error -> {
                Resource.Error()
            }

            is Resource.Success -> {
                offlineDiaryRepository.insertProductDiaryEntry(productDiaryEntry = resource.data)
            }
        }
    }
}