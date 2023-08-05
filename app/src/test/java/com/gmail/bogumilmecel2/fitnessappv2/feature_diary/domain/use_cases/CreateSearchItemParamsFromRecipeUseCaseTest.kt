package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.BaseMockkTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.Diary.SERVING
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.Diary.SERVINGS
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search.componens.SearchItemParams
import io.mockk.every
import org.junit.Test
import kotlin.test.assertEquals

class CreateSearchItemParamsFromRecipeUseCaseTest: BaseMockkTest() {

    companion object {
        private const val KCAL = "kcal"
    }

    private val createSearchItemParamsFromRecipeUseCase = CreateSearchItemParamsFromRecipeUseCase(resourceProvider = resourceProvider)

    @Test
    fun `Check if params are correct`() {
        verifyParams(
            recipeName = "recipe name",
            servings = 1,
            totalRecipeCalories = 216
        )
    }

    @Test
    fun `Check if params are correct 2`() {
        verifyParams(
            recipeName = "recipe name 2",
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
        val caloriesPerServing = (nutritionValues.calories*(1.0/servings.toDouble())).toInt()

        mockPluralStrings(quantity = servings)
        mockKcal(calories = caloriesPerServing)

        val recipe = Recipe(
            name = recipeName,
            servings = servings,
            nutritionValues = nutritionValues
        )

        val onClick = {}
        val onLongClick = {}
        val servingText = if (servings == 1) SERVING else SERVINGS

        assertEquals(
            actual = createSearchItemParamsFromRecipeUseCase(recipe, onClick = onClick, onLongClick = onLongClick),
            expected = SearchItemParams(
                name = recipeName,
                textBelowName = "$servings $servingText",
                endText = "$caloriesPerServing $KCAL",
                onItemClick = onClick,
                onItemLongClick = onLongClick
            )
        )
    }

    private fun mockKcal(calories: Int) {
        every { resourceProvider.getString(R.string.kcal_with_value, calories) } returns "$calories $KCAL"
    }

    private fun mockPluralStrings(quantity: Int) {
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
}