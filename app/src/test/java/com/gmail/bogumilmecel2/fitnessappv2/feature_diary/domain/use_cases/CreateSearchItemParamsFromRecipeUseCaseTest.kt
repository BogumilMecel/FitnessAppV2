package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseMockkTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.Diary.KCAL
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.Diary.RECIPE_NAME_1
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.Diary.RECIPE_NAME_2
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.Diary.SERVING
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.Diary.SERVINGS
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.Diary.mockKcalWithValue
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.Diary.mockServingsStrings
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens.SearchItemParams
import org.junit.Test
import kotlin.test.assertEquals

class CreateSearchItemParamsFromRecipeUseCaseTest : BaseMockkTest() {

    private val createSearchItemParamsFromRecipeUseCase =
        CreateSearchItemParamsFromRecipeUseCase(resourceProvider = resourceProvider)

    @Test
    fun `Check if params are correct`() {
        verifyParams(
            recipeName = RECIPE_NAME_1,
            servings = 1,
            totalRecipeCalories = 216
        )
    }

    @Test
    fun `Check if params are correct 2`() {
        verifyParams(
            recipeName = RECIPE_NAME_2,
            servings = 4,
            totalRecipeCalories = 400
        )
    }

    private fun verifyParams(
        recipeName: String,
        servings: Int,
        totalRecipeCalories: Int
    ) {
        val nutritionValues = NutritionValues(calories = totalRecipeCalories)
        val caloriesPerServing = (nutritionValues.calories * (1.0 / servings.toDouble())).toInt()

        mockServingsStrings(
            quantity = servings,
            resourceProvider = resourceProvider
        )
        mockKcalWithValue(
            calories = caloriesPerServing,
            resourceProvider = resourceProvider
        )

        val recipe = Recipe(
            name = recipeName,
            servings = servings,
            nutritionValues = nutritionValues
        )

        val onClick = {}
        val onLongClick = {}
        val servingText = if (servings == 1) SERVING else SERVINGS

        assertEquals(
            actual = createSearchItemParamsFromRecipeUseCase(
                recipe,
                onClick = onClick,
                onLongClick = onLongClick
            ),
            expected = SearchItemParams(
                name = recipeName,
                textBelowName = "$servings $servingText",
                endText = "$caloriesPerServing $KCAL",
                onItemClick = onClick,
                onItemLongClick = onLongClick
            )
        )
    }
}