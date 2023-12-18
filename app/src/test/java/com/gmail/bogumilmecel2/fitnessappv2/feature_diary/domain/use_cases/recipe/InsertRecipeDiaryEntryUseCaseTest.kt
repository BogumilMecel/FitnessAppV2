package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertIs

class PostRecipeDiaryEntryUseCaseTest : BaseTest() {

    private val diaryRepository = mockk<DiaryRepository>()
    private val offlineDiaryRepository = mockk<OfflineDiaryRepository>()
    private val calculateRecipeNutritionValuesForServingsUseCase =
        mockk<CalculateRecipeNutritionValuesForServingsUseCase>()
    private val postRecipeDiaryEntryUseCase = PostRecipeDiaryEntryUseCase(
        diaryRepository = diaryRepository,
        resourceProvider = resourceProvider,
        calculateRecipeNutritionValuesForServingsUseCase = calculateRecipeNutritionValuesForServingsUseCase,
        offlineDiaryRepository = offlineDiaryRepository
    )

    @Test
    fun `Check if servings are not valid integer, resource error is returned`() = runTest {
        callTestedMethod(servings = MockConstants.Diary.INVALID_WEIGHT_OR_SERVINGS)
            .assertIsError(resourceProvider.getString(R.string.recipe_servings_error))
    }

    @Test
    fun `Check if servings are 0, resource error is returned`() = runTest {
        callTestedMethod(servings = MockConstants.Diary.ZERO_RECIPE_SERVINGS)
            .assertIsError(resourceProvider.getString(R.string.recipe_servings_error))
    }

    @Test
    fun `Check if servings are less than 0, resource error is returned`() = runTest {
        callTestedMethod(servings = MockConstants.Diary.NEGATIVE_RECIPE_SERVINGS)
            .assertIsError(resourceProvider.getString(R.string.recipe_servings_error))
    }

    @Test
    fun `Check if data is correct and repository returns resource error, resource error is returned`() =
        runTest {
            coEvery { calculateRecipeNutritionValuesForServingsUseCase(any(), any()) } returns NutritionValues()
            coEvery { diaryRepository.insertRecipeDiaryEntry(recipeDiaryEntryRequest = any()) } returns Resource.Error()
            assertIs<Resource.Error<Unit>>(callTestedMethod())
        }

    @Test
    fun `Check if data is correct and repository returns resource success, resource success is returned`() =
        runTest {
            val sampleNutritionValues = MockConstants.Diary.getSampleNutritionValues()
            val expectedRequest = RecipeDiaryEntryRequest(
                recipeId = MockConstants.Diary.RECIPE_ID_1,
                servings = MockConstants.Diary.CORRECT_RECIPE_SERVINGS_2.toValidIntOrThrow(),
                date = MockConstants.MOCK_DATE_2021,
                mealName = MealName.BREAKFAST,
                nutritionValues = sampleNutritionValues
            )
            val expectedRecipeDiaryEntry = RecipeDiaryEntry(
                recipeId = MockConstants.Diary.RECIPE_ID_1,
                servings = MockConstants.Diary.CORRECT_RECIPE_SERVINGS_2.toValidIntOrThrow(),
                date = MockConstants.MOCK_DATE_2021,
                mealName = MealName.BREAKFAST,
                nutritionValues = sampleNutritionValues
            )
            every {
                calculateRecipeNutritionValuesForServingsUseCase(
                    any(),
                    any()
                )
            } returns sampleNutritionValues
            coEvery { diaryRepository.insertRecipeDiaryEntry(recipeDiaryEntryRequest = expectedRequest) } returns Resource.Success(expectedRecipeDiaryEntry)
            coEvery { diaryRepository.insertOfflineDiaryEntry(expectedRecipeDiaryEntry) } returns Resource.Success(Unit)
            coEvery { offlineDiaryRepository.insertRecipe(recipe = any()) } returns Resource.Success(Unit)
            assertIs<Resource.Success<Unit>>(callTestedMethod())
            coVerify(exactly = 1) { diaryRepository.insertRecipeDiaryEntry(recipeDiaryEntryRequest = expectedRequest) }
            coVerify(exactly = 1) { diaryRepository.insertOfflineDiaryEntry(expectedRecipeDiaryEntry) }
        }

    private suspend fun callTestedMethod(
        recipeId: String = MockConstants.Diary.RECIPE_ID_1,
        servings: String = MockConstants.Diary.CORRECT_RECIPE_SERVINGS_2,
        date: String = MockConstants.MOCK_DATE_2021
    ) = postRecipeDiaryEntryUseCase(
        recipe = Recipe(id = recipeId),
        servingsString = servings,
        date = date,
        mealName = MealName.BREAKFAST
    )
}