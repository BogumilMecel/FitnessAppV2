package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertIs

class EditRecipeDiaryEntryUseCaseTest : BaseTest() {

    private val diaryRepository = mockk<DiaryRepository>()
    private val offlineDiaryEntry = mockk<OfflineDiaryRepository>()
    private val calculateRecipeNutritionValuesForServingsUseCase =
        mockk<CalculateRecipeNutritionValuesForServingsUseCase>()
    private val editRecipeDiaryEntryUseCase = EditRecipeDiaryEntryUseCase(
        diaryRepository = diaryRepository,
        calculateRecipeNutritionValuesForServingsUseCase = calculateRecipeNutritionValuesForServingsUseCase,
        offlineDiaryRepository = offlineDiaryEntry
    )

    @Test
    fun `Check if new servings are not valid integer, resource error is returned`() = runTest {
        assertIs<Resource.Error<Unit>>(
            callTestedMethod(
                newServings = MockConstants.Diary.INVALID_WEIGHT_OR_SERVINGS
            )
        )
    }

    @Test
    fun `Check if new servings are 0, resource error is returned`() = runTest {
        assertIs<Resource.Error<Unit>>(
            callTestedMethod(
                newServings = MockConstants.Diary.ZERO_RECIPE_SERVINGS
            )
        )
    }

    @Test
    fun `Check if new servings are less than 0, resource error is returned`() = runTest {
        assertIs<Resource.Error<Unit>>(
            callTestedMethod(
                newServings = MockConstants.Diary.NEGATIVE_RECIPE_SERVINGS
            )
        )
    }

    @Test
    fun `Check if new servings are the same as original, resource error is returned`() = runTest {
        assertIs<Resource.Error<Unit>>(
            callTestedMethod(
                newServings = MockConstants.Diary.CORRECT_RECIPE_SERVINGS_3,
                originalServings = MockConstants.Diary.CORRECT_RECIPE_SERVINGS_3.toValidIntOrThrow()
            )
        )
    }

    @Test
    fun `Check if repository returns resource error, resource error is returned`() = runTest {
        coEvery { calculateRecipeNutritionValuesForServingsUseCase(recipe = any(), servings = any()) } returns NutritionValues()
        coEvery { diaryRepository.editRecipeDiaryEntry(recipeDiaryEntry = any()) } returns Resource.Error()
        assertIs<Resource.Error<Unit>>(callTestedMethod())
    }

    @Test
    fun `Check if repository returns resource success, resource success is returned`() = runTest {
        val servings = MockConstants.Diary.CORRECT_RECIPE_SERVINGS_3.toValidIntOrThrow()
        val expectedNutritionValues = NutritionValues(
            calories = 215,
            carbohydrates = 73.1,
            protein = 78.5,
            fat = 89.1
        )
        val expectedRecipeDiaryEntry = RecipeDiaryEntry(
            id = MockConstants.Diary.RECIPE_DIARY_ENTRY_ID_41,
            nutritionValues = expectedNutritionValues,
            servings = servings
        )
        coEvery {
            calculateRecipeNutritionValuesForServingsUseCase(
                recipe = any(),
                servings = servings
            )
        } returns expectedNutritionValues
        coEvery { diaryRepository.editRecipeDiaryEntry(recipeDiaryEntry = expectedRecipeDiaryEntry) } returns Resource.Success(expectedRecipeDiaryEntry)
        coEvery { offlineDiaryEntry.insertRecipeDiaryEntry(expectedRecipeDiaryEntry) } returns Resource.Success(Unit)
        assertIs<Resource.Success<Unit>>(callTestedMethod())
        coVerify(exactly = 1) {
            diaryRepository.editRecipeDiaryEntry(recipeDiaryEntry = expectedRecipeDiaryEntry)
            offlineDiaryEntry.insertRecipeDiaryEntry(expectedRecipeDiaryEntry)
        }
    }

    private suspend fun callTestedMethod(
        recipeDiaryEntryId: String = MockConstants.Diary.RECIPE_DIARY_ENTRY_ID_41,
        newServings: String = MockConstants.Diary.CORRECT_RECIPE_SERVINGS_3,
        originalServings: Int = MockConstants.Diary.CORRECT_RECIPE_SERVINGS_2.toValidIntOrThrow()
    ) = editRecipeDiaryEntryUseCase(
        recipe = Recipe(),
        newServingsStringValue = newServings,
        recipeDiaryEntry = RecipeDiaryEntry(
            id = recipeDiaryEntryId,
            servings = originalServings
        )
    )
}