package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_product

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidDouble
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.toValidInt
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NewProductRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NutritionValuesIn
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class SaveNewProductUseCase(
    private val diaryRepository: DiaryRepository,
    private val resourceProvider: ResourceProvider,
) {

    suspend operator fun invoke(
        name: String,
        containerWeight: String,
        nutritionValuesIn: NutritionValuesIn,
        measurementUnit: MeasurementUnit,
        calories: String,
        carbohydrates: String,
        protein: String,
        fat: String,
        barcode: String,
    ): Resource<Product> {
        val caloriesValue = calories.toValidInt()
        val carbohydratesValue = carbohydrates.toValidDouble()
        val proteinValue = protein.toValidDouble()
        val fatValue = fat.toValidDouble()
        val containerWeightValue = containerWeight.toValidInt()

        if (containerWeight.isNotEmpty() && containerWeightValue == null) {
            return Resource.Error(resourceProvider.getString(R.string.new_product_bad_container_weight))
        }

        if ((containerWeightValue == null || containerWeightValue <= 0) && nutritionValuesIn != NutritionValuesIn.HUNDRED_GRAMS) {
            return Resource.Error(resourceProvider.getString(R.string.new_product_bad_container_weight))
        }

        return if (name.isBlank()) {
            Resource.Error(resourceProvider.getString(R.string.new_product_empty_name))
        } else if (name.length > 48) {
            Resource.Error(resourceProvider.getString(R.string.new_product_wrong_name_length_long))
        } else if (name.length < 4) {
            Resource.Error(resourceProvider.getString(R.string.new_product_wrong_name_length_short))
        } else if (caloriesValue == null || carbohydratesValue == null || proteinValue == null || fatValue == null) {
            Resource.Error(resourceProvider.getString(R.string.new_product_bad_nutrition_values))
        } else {
            diaryRepository.saveNewProduct(
                newProductRequest = NewProductRequest(
                    name = name,
                    measurementUnit = measurementUnit,
                    containerWeight = containerWeightValue,
                    barcode = barcode.ifEmpty { null },
                    nutritionValues = NutritionValues(
                        calories = caloriesValue,
                        carbohydrates = carbohydratesValue,
                        protein = proteinValue,
                        fat = fatValue
                    ),
                    nutritionValuesIn = nutritionValuesIn
                )
            )
        }
    }
}