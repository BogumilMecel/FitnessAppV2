package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.BaseMockkTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntryPostRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class InsertProductDiaryEntryUseCaseTest: BaseMockkTest() {

    companion object {
        private const val WEIGHT_ERROR = "weight_error"
    }

    private val diaryRepository = mockk<DiaryRepository>()
    private val insertProductDiaryEntryUseCase = InsertProductDiaryEntryUseCase(
        diaryRepository = diaryRepository,
        resourceProvider = resourceProvider
    )

    @Before
    fun setUp() {
        mockString(
            resId = R.string.incorrect_weight_was_entered,
            value = WEIGHT_ERROR
        )
    }

    @Test
    fun `Check if weight is not valid integer, resource error is returned`() = runTest {
        val resource = callTestedMethod(weight = MockConstants.Diary.INVALID_WEIGHT_OR_SERVINGS)
        assertIs<Resource.Error<Unit>>(resource)
        checkErrorMessage(resource)
    }

    @Test
    fun `Check if weight is 0, resource error is returned`() = runTest {
        val resource = callTestedMethod(weight = MockConstants.Diary.ZERO_PRODUCT_DIARY_ENTRY_WEIGHT)
        assertIs<Resource.Error<Unit>>(resource)
        checkErrorMessage(resource)
    }

    @Test
    fun `Check if weight is less than 0, resource error is returned`() = runTest {
        val resource = callTestedMethod(weight = MockConstants.Diary.NEGATIVE_RECIPE_SERVINGS)
        assertIs<Resource.Error<Unit>>(resource)
        checkErrorMessage(resource)
    }

    @Test
    fun `Check if data is correct and repository returns resource error, resource error is returned`() = runTest {
        coEvery { diaryRepository.insertProductDiaryEntry(productDiaryEntryPostRequest = any()) } returns Resource.Error()
        assertIs<Resource.Error<Unit>>(callTestedMethod())
    }

    @Test
    fun `Check if data is correct and repository returns resource success, resource success is returned`() = runTest {
        val expectedRequest = ProductDiaryEntryPostRequest(
            productId = MockConstants.Diary.PRODUCT_ID_11,
            weight = MockConstants.Diary.CORRECT_PRODUCT_DIARY_ENTRY_WEIGHT_1.toValidIntOrThrow(),
            date = MockConstants.MOCK_DATE_2021,
            mealName = MealName.BREAKFAST,
        )
        coEvery { diaryRepository.insertProductDiaryEntry(productDiaryEntryPostRequest = expectedRequest) } returns Resource.Success(Unit)
        assertIs<Resource.Success<Unit>>(callTestedMethod())
        coVerify(exactly = 1) { diaryRepository.insertProductDiaryEntry(productDiaryEntryPostRequest = expectedRequest) }
    }

    private fun checkErrorMessage(resource: Resource.Error<Unit>) {
        assertEquals(
            expected = WEIGHT_ERROR,
            actual = resource.uiText
        )
    }

    private suspend fun callTestedMethod(
        productId: String = MockConstants.Diary.PRODUCT_ID_11,
        weight: String = MockConstants.Diary.CORRECT_PRODUCT_DIARY_ENTRY_WEIGHT_1,
        date: String = MockConstants.MOCK_DATE_2021
    ) = insertProductDiaryEntryUseCase(
        productId = productId,
        weightStringValue = weight,
        date = date,
        mealName = MealName.BREAKFAST
    )
}