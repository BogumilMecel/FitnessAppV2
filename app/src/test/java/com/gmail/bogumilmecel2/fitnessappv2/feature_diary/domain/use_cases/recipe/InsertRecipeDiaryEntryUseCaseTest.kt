package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.BaseMockkTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class PostRecipeDiaryEntryUseCaseTest : BaseMockkTest() {

    companion object {
        private const val SERVINGS_ERROR = "servings_error"
    }

    private val diaryRepository = mockk<DiaryRepository>()
    private val postRecipeDiaryEntryUseCase = PostRecipeDiaryEntryUseCase(
        diaryRepository = diaryRepository,
        resourceProvider = resourceProvider
    )

    @Before
    fun setUp() {
        mockString(
            resId = R.string.recipe_servings_error,
            value = SERVINGS_ERROR
        )
    }

    @Test
    fun `Check if servings are not valid integer, resource error is returned`() = runTest {
        val resource = callTestedMethod(servings = MockConstants.Diary.INVALID_WEIGHT_OR_SERVINGS)
        assertIs<Resource.Error<Unit>>(resource)
        checkErrorMessage(resource)
    }

    @Test
    fun `Check if servings are 0, resource error is returned`() = runTest {
        val resource = callTestedMethod(servings = MockConstants.Diary.ZERO_RECIPE_SERVINGS)
        assertIs<Resource.Error<Unit>>(resource)
        checkErrorMessage(resource)
    }

    @Test
    fun `Check if servings are less than 0, resource error is returned`() = runTest {
        val resource = callTestedMethod(servings = MockConstants.Diary.NEGATIVE_RECIPE_SERVINGS)
        assertIs<Resource.Error<Unit>>(resource)
        checkErrorMessage(resource)
    }

    @Test
    fun `Check if data is correct and repository returns resource error, resource error is returned`() = runTest {
        coEvery { diaryRepository.addRecipeDiaryEntry(recipeDiaryEntryRequest = any()) } returns Resource.Error()
        assertIs<Resource.Error<Unit>>(callTestedMethod())
    }

    @Test
    fun `Check if data is correct and repository returns resource success, resource success is returned`() = runTest {
        val expectedRequest = RecipeDiaryEntryRequest(
            recipeId = MockConstants.Diary.RECIPE_DIARY_ENTRY_ID_41,
            servings = MockConstants.Diary.CORRECT_RECIPE_SERVINGS_2.toValidIntOrThrow(),
            date = MockConstants.MOCK_DATE_2021,
            mealName = MealName.BREAKFAST,
        )
        coEvery { diaryRepository.addRecipeDiaryEntry(recipeDiaryEntryRequest = expectedRequest) } returns Resource.Success(Unit)
        assertIs<Resource.Success<Unit>>(callTestedMethod())
        coVerify(exactly = 1) { diaryRepository.addRecipeDiaryEntry(recipeDiaryEntryRequest = expectedRequest) }
    }

    private fun checkErrorMessage(resource: Resource.Error<Unit>) {
        assertEquals(
            expected = SERVINGS_ERROR,
            actual = resource.uiText
        )
    }

    private suspend fun callTestedMethod(
        recipeDiaryEntryId: String = MockConstants.Diary.RECIPE_DIARY_ENTRY_ID_41,
        servings: String = MockConstants.Diary.CORRECT_RECIPE_SERVINGS_2,
        date: String = MockConstants.MOCK_DATE_2021
    ) = postRecipeDiaryEntryUseCase(
        recipeId = recipeDiaryEntryId,
        servingsString = servings,
        date = date,
        mealName = MealName.BREAKFAST
    )
}