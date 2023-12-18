package com.gmail.bogumilmecel2.fitnessappv2.common

object MockConstants {
    const val MOCK_DATE_2021 = "2021-12-12"

    object Diary {
        const val RECIPE_DIARY_ENTRY_ID_41 = "41"

        private const val NEGATIVE_PRODUCT_DIARY_ENTRY_WEIGHT = "-1"
        private const val ZERO_PRODUCT_DIARY_ENTRY_WEIGHT = "0"

        const val CORRECT_RECIPE_SERVINGS_1 = "2"
        const val CORRECT_RECIPE_SERVINGS_2 = "3"
        const val NEGATIVE_RECIPE_SERVINGS = NEGATIVE_PRODUCT_DIARY_ENTRY_WEIGHT
        const val ZERO_RECIPE_SERVINGS = ZERO_PRODUCT_DIARY_ENTRY_WEIGHT
        const val INVALID_RECIPE_SERVINGS = "abc"
    }
}