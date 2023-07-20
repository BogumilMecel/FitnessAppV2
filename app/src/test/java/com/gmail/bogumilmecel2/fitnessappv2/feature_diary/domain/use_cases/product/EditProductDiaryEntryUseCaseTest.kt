package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseMockkTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.EditProductDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertIs

class EditProductDiaryEntryUseCaseTest: BaseMockkTest() {

    private val diaryRepository = mockk<DiaryRepository>()
    private val editProductDiaryEntryUseCase = EditProductDiaryEntryUseCase(diaryRepository = diaryRepository)

    @Test
    fun `Check if weight is not valid integer, resource error is returned`() = runTest {
        val resource = callTestedMethod(weight = MockConstants.Diary.INVALID_WEIGHT_OR_SERVINGS)
        assertIs<Resource.Error<Unit>>(resource)
    }

    @Test
    fun `Check if weight is 0, resource error is returned`() = runTest {
        val resource = callTestedMethod(weight = MockConstants.Diary.ZERO_PRODUCT_DIARY_ENTRY_WEIGHT)
        assertIs<Resource.Error<Unit>>(resource)
    }

    @Test
    fun `Check if weight is less than 0, resource error is returned`() = runTest {
        val resource = callTestedMethod(weight = MockConstants.Diary.NEGATIVE_RECIPE_SERVINGS)
        assertIs<Resource.Error<Unit>>(resource)
    }

    @Test
    fun `Check if data is correct and repository returns resource error, resource error is returned`() = runTest {
        coEvery { diaryRepository.editProductDiaryEntry(editProductDiaryEntryRequest = any()) } returns Resource.Error()
        assertIs<Resource.Error<Unit>>(callTestedMethod())
    }

    @Test
    fun `Check if data is correct and repository returns resource success, resource success is returned`() = runTest {
        val expectedRequest = EditProductDiaryEntryRequest(
            productDiaryEntryId = MockConstants.Diary.PRODUCT_DIARY_ENTRY_ID_21,
            newWeight = MockConstants.Diary.CORRECT_PRODUCT_DIARY_ENTRY_WEIGHT_1.toValidIntOrThrow(),
        )
        coEvery { diaryRepository.editProductDiaryEntry(editProductDiaryEntryRequest = expectedRequest) } returns Resource.Success(Unit)
        assertIs<Resource.Success<Unit>>(callTestedMethod())
        coVerify(exactly = 1) { diaryRepository.editProductDiaryEntry(editProductDiaryEntryRequest = expectedRequest) }
    }

    private suspend fun callTestedMethod(
        productDiaryEntryId: String = MockConstants.Diary.PRODUCT_DIARY_ENTRY_ID_21,
        weight: String = MockConstants.Diary.CORRECT_PRODUCT_DIARY_ENTRY_WEIGHT_1,
    ) = editProductDiaryEntryUseCase(
        productDiaryEntryId = productDiaryEntryId,
        newWeightStringValue = weight,
    )
}