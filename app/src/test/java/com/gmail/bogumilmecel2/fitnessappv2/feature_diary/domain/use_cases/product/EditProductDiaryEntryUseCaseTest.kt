package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertIs

class EditProductDiaryEntryUseCaseTest: BaseTest() {

    private val diaryRepository = mockk<DiaryRepository>()
    private val calculateProductNutritionValuesUseCase = mockk<CalculateProductNutritionValuesUseCase>()
    private val editProductDiaryEntryUseCase = EditProductDiaryEntryUseCase(
        diaryRepository = diaryRepository,
        calculateProductNutritionValuesUseCase = calculateProductNutritionValuesUseCase
    )

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
    fun `Check if weight is the same as original weight, resource error is returned`() = runTest {
        val resource = callTestedMethod(originalWeight = MockConstants.Diary.CORRECT_PRODUCT_DIARY_ENTRY_WEIGHT_1.toValidIntOrThrow())
        assertIs<Resource.Error<Unit>>(resource)
    }

    @Test
    fun `Check if weight is less than 0, resource error is returned`() = runTest {
        val resource = callTestedMethod(weight = MockConstants.Diary.NEGATIVE_RECIPE_SERVINGS)
        assertIs<Resource.Error<Unit>>(resource)
    }

    @Test
    fun `Check if data is correct and repository returns resource error, resource error is returned`() = runTest {
        coEvery { calculateProductNutritionValuesUseCase(weight = any(), product = any()) } returns NutritionValues()
        coEvery { diaryRepository.editProductDiaryEntry(productDiaryEntry = any()) } returns Resource.Error()
        assertIs<Resource.Error<Unit>>(callTestedMethod())
    }

    @Test
    fun `Check if data is correct and repository returns resource success, resource success is returned`() = runTest {
        val weight = MockConstants.Diary.CORRECT_PRODUCT_DIARY_ENTRY_WEIGHT_1.toValidIntOrThrow()
        val expectedNutritionValues = NutritionValues(
            calories = 215,
            carbohydrates = 73.1,
            protein = 78.5,
            fat = 89.1
        )
        val expectedProductDiaryEntry = ProductDiaryEntry(
            id = MockConstants.Diary.PRODUCT_DIARY_ENTRY_ID_21,
            weight = weight,
            nutritionValues = expectedNutritionValues
        )
        coEvery {
            calculateProductNutritionValuesUseCase(
                weight = weight,
                product = any()
            )
        } returns expectedNutritionValues
        coEvery { diaryRepository.editProductDiaryEntry(productDiaryEntry = expectedProductDiaryEntry) } returns Resource.Success(expectedProductDiaryEntry)
        coEvery { diaryRepository.insertOfflineDiaryEntry(expectedProductDiaryEntry) } returns Resource.Success(Unit)
        assertIs<Resource.Success<Unit>>(callTestedMethod())
        coVerify(exactly = 1) {
            diaryRepository.editProductDiaryEntry(productDiaryEntry = expectedProductDiaryEntry)
            diaryRepository.insertOfflineDiaryEntry(expectedProductDiaryEntry)
        }
    }

    private suspend fun callTestedMethod(
        productDiaryEntryId: String = MockConstants.Diary.PRODUCT_DIARY_ENTRY_ID_21,
        weight: String = MockConstants.Diary.CORRECT_PRODUCT_DIARY_ENTRY_WEIGHT_1,
        originalWeight: Int = MockConstants.Diary.CORRECT_PRODUCT_DIARY_ENTRY_WEIGHT_2.toValidIntOrThrow()
    ) = editProductDiaryEntryUseCase(
        productDiaryEntry = ProductDiaryEntry(
            id = productDiaryEntryId,
            weight = originalWeight
        ),
        newWeightStringValue = weight,
        product = Product(),
    )
}