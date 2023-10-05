package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.BaseMockkTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import org.junit.Test
import kotlin.test.assertEquals

class GenerateNewRecipeSearchTitleUseCaseTest: BaseMockkTest() {

    private val generateNewRecipeSearchTitleUseCase = GenerateNewRecipeSearchTitleUseCase(
        resourceProvider = resourceProvider
    )

    @Test
    fun `Check if title is correctly generated if recipe name is not empty`() {
        assertEquals(
            expected = MockConstants.Diary.RECIPE_NAME_1,
            actual = generateNewRecipeSearchTitleUseCase(
                recipeName = MockConstants.Diary.RECIPE_NAME_1
            )
        )
    }

    @Test
    fun `Check if title is correctly generated if recipe name is empty`() {
        val addIngredientText = "Add Ingredient Text"
        mockString(
            resId = R.string.search_add_ingredient,
            value = addIngredientText
        )
        assertEquals(
            expected = addIngredientText,
            actual = generateNewRecipeSearchTitleUseCase(
                recipeName = ""
            )
        )
    }
}