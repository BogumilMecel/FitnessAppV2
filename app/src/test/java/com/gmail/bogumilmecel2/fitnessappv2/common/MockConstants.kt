package com.gmail.bogumilmecel2.fitnessappv2.common

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import io.mockk.every

object MockConstants {
    const val MOCK_DATE_2021 = "2021-12-12"
    const val TIMESTAMP = 123456789L
    const val USER_ID = "user_id"
    const val CORRECT_PAGE = 1

    object Auth {
        const val CORRECT_EMAIL = "email@email.com"
        const val CORRECT_PASSWORD = "123456"
        const val CORRECT_PASSWORD_2 = "654321"
        const val CORRECT_USERNAME = "username"
        const val TOKEN = "123456789"
    }

    object Diary {
        const val PRODUCT_ID_11 = "11"
        const val PRODUCT_DIARY_ENTRY_ID_21 = "21"
        const val RECIPE_ID_1 = "recipe_id_1"
        const val RECIPE_DIARY_ENTRY_ID_41 = "41"
        const val RECIPE_NAME_1 = "Rice And Chicken"
        const val RECIPE_NAME_2 = "Chicken And Rice"

        const val PRODUCT_NAME_1 = "Rice"
        const val PRODUCT_NAME_2 = "Chicken"

        const val CORRECT_PRODUCT_DIARY_ENTRY_WEIGHT_1 = "25"
        const val CORRECT_PRODUCT_DIARY_ENTRY_WEIGHT_2 = "50"
        const val NEGATIVE_PRODUCT_DIARY_ENTRY_WEIGHT = "-1"
        const val ZERO_PRODUCT_DIARY_ENTRY_WEIGHT = "0"

        const val CORRECT_RECIPE_SERVINGS_2 = "2"
        const val CORRECT_RECIPE_SERVINGS_3 = "3"
        const val CORRECT_RECIPE_SERVINGS_1 = "1"
        const val NEGATIVE_RECIPE_SERVINGS = NEGATIVE_PRODUCT_DIARY_ENTRY_WEIGHT
        const val ZERO_RECIPE_SERVINGS = ZERO_PRODUCT_DIARY_ENTRY_WEIGHT
        const val INVALID_WEIGHT_OR_SERVINGS = "abc"

        const val SERVING = "serving"
        const val SERVINGS = "servings"

        const val GRAMS = "g"
        const val MILLILITERS = "ml"
        const val KCAL = "kcal"

        fun getSampleProduct() = Product(
            id = PRODUCT_ID_11,
            name = PRODUCT_NAME_1,
            utcTimestamp = TIMESTAMP,
            nutritionValues = getSampleNutritionValues(),
            userId = USER_ID
        )

        fun getSampleRecipe() = Recipe(
            id = RECIPE_ID_1,
            name = RECIPE_NAME_1,
            utcTimestamp = TIMESTAMP,
            nutritionValues = getSampleNutritionValues(),
            userId = USER_ID
        )

        fun getSampleNutritionValues() = NutritionValues(calories = 255, carbohydrates = 31.0, protein = 17.0, fat = 7.0)

        fun mockMeasurementUnitWithValueString(
            resourceProvider: ResourceProvider,
            value: Int
        ) {
            every {
                resourceProvider.getString(
                    stringResId = MeasurementUnit.GRAMS.getStringResWithValue(),
                    value
                )
            } returns "$value$GRAMS"
            every {
                resourceProvider.getString(
                    stringResId = MeasurementUnit.MILLILITERS.getStringResWithValue(),
                    value
                )
            } returns "$value$MILLILITERS"
        }

        fun mockServingsStrings(
            quantity: Int,
            resourceProvider: ResourceProvider
        ) {
            every {
                resourceProvider.getPluralString(
                    pluralResId = R.plurals.servings,
                    quantity = quantity
                )
            } returns when(quantity) {
                1 -> "$quantity $SERVING"
                else -> "$quantity $SERVINGS"
            }
        }

        fun mockKcalWithValue(
            calories: Int,
            resourceProvider: ResourceProvider
        ) {
            every { resourceProvider.getString(R.string.kcal_with_value, calories) } returns "$calories $KCAL"
        }
    }
}