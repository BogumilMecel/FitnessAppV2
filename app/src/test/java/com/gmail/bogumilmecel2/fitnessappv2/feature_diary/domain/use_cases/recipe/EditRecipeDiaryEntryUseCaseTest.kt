package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseMockkTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.EditRecipeDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertIs

class EditRecipeDiaryEntryUseCaseTest : BaseMockkTest() {

    private val diaryRepository = mockk<DiaryRepository>()
    private val editRecipeDiaryEntryUseCase =
        EditRecipeDiaryEntryUseCase(diaryRepository = diaryRepository)

    @Test
    fun `Check if new servings are not valid integer, resource error is returned`() = runTest {
        assertIs<Resource.Error<Unit>>(
            callTestedMethod(
                newServings = MockConstants.Diary.INVALID_WEIGHT_OR_SERVINGS
            )
        )
    }

    @Test
    fun `Check if original servings are not valid integer, resource error is returned`() = runTest {
        assertIs<Resource.Error<Unit>>(
            callTestedMethod(
                originalServings = MockConstants.Diary.INVALID_WEIGHT_OR_SERVINGS
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
                originalServings = MockConstants.Diary.CORRECT_RECIPE_SERVINGS_3
            )
        )
    }

    @Test
    fun `Check if repository returns resource error, resource error is returned`() = runTest {
        coEvery { diaryRepository.editRecipeDiaryEntry(editRecipeDiaryEntryRequest = any()) } returns Resource.Error()
        assertIs<Resource.Error<Unit>>(callTestedMethod())
    }

    @Test
    fun `Check if repository returns resource success, resource success is returned`() = runTest {
        coEvery { diaryRepository.editRecipeDiaryEntry(editRecipeDiaryEntryRequest = any()) } returns Resource.Success(Unit)
        assertIs<Resource.Success<Unit>>(callTestedMethod())
        coVerify(exactly = 1) {
            diaryRepository.editRecipeDiaryEntry(
                editRecipeDiaryEntryRequest = EditRecipeDiaryEntryRequest(
                    recipeDiaryEntryId = MockConstants.Diary.RECIPE_DIARY_ENTRY_ID_41,
                    newServings = MockConstants.Diary.CORRECT_RECIPE_SERVINGS_3.toValidIntOrThrow()
                )
            )
        }
    }

    private suspend fun callTestedMethod(
        recipeDiaryEntryId: String = MockConstants.Diary.RECIPE_DIARY_ENTRY_ID_41,
        newServings: String = MockConstants.Diary.CORRECT_RECIPE_SERVINGS_3,
        originalServings: String = MockConstants.Diary.CORRECT_RECIPE_SERVINGS_2
    ) = editRecipeDiaryEntryUseCase(
        recipeDiaryEntryId = recipeDiaryEntryId,
        newServingsStringValue = newServings,
        originalServingsStringValue = originalServings
    )
}