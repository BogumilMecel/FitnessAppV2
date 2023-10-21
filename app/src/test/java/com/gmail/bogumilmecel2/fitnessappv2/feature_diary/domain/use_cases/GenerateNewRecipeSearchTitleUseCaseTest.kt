package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import org.junit.Test
import kotlin.test.assertEquals

class GenerateNewRecipeSearchTitleUseCaseTest: BaseTest() {

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
        assertEquals(
            expected = resourceProvider.getString(R.string.search_add_ingredient),
            actual = generateNewRecipeSearchTitleUseCase(
                recipeName = ""
            )
        )
    }
}