package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.Test
import kotlin.test.assertIs

class InsertProductDiaryEntryUseCaseTest: BaseTest() {

    private val diaryRepository = mockk<DiaryRepository>()
    private val offlineDiaryRepository = mockk<OfflineDiaryRepository>()
    private val calculateProductNutritionValuesUseCase = mockk<CalculateProductNutritionValuesUseCase>()
    private val insertProductDiaryEntryUseCase = InsertProductDiaryEntryUseCase(
        diaryRepository = diaryRepository,
        resourceProvider = resourceProvider,
        calculateProductNutritionValuesUseCase = calculateProductNutritionValuesUseCase,
        offlineDiaryRepository = offlineDiaryRepository
    )

    @Test
    fun `Check if weight is not valid integer, resource error is returned`() = runTest {
        callTestedMethod(weight = MockConstants.Diary.INVALID_WEIGHT_OR_SERVINGS)
            .assertIsError(resourceProvider.getString(R.string.incorrect_weight_was_entered))
    }

    @Test
    fun `Check if weight is 0, resource error is returned`() = runTest {
        callTestedMethod(weight = MockConstants.Diary.ZERO_PRODUCT_DIARY_ENTRY_WEIGHT)
            .assertIsError(resourceProvider.getString(R.string.incorrect_weight_was_entered))
    }

    @Test
    fun `Check if weight is less than 0, resource error is returned`() = runTest {
        callTestedMethod(weight = MockConstants.Diary.NEGATIVE_RECIPE_SERVINGS)
            .assertIsError(resourceProvider.getString(R.string.incorrect_weight_was_entered))
    }

    @Test
    fun `Check if data is correct and repository returns resource error, resource error is returned`() = runTest {
        coEvery { calculateProductNutritionValuesUseCase(any(), any()) } returns NutritionValues()
        coEvery { diaryRepository.insertProductDiaryEntry(productDiaryEntry = any()) } returns Resource.Error()
        callTestedMethod().assertIsError()
    }

    @Test
    fun `Check if data is correct and repository returns resource success, resource success is returned`() = runTest {
        val sampleNutritionValues = MockConstants.Diary.getSampleNutritionValues()
        val expectedProductDiaryEntry = ProductDiaryEntry(
            productId = MockConstants.Diary.PRODUCT_ID_11,
            weight = MockConstants.Diary.CORRECT_PRODUCT_DIARY_ENTRY_WEIGHT_1.toValidIntOrThrow(),
            date = MockConstants.getDate2021(),
            mealName = MealName.BREAKFAST,
            nutritionValues = sampleNutritionValues
        )
        coEvery { calculateProductNutritionValuesUseCase(any(), any()) } returns sampleNutritionValues
        coEvery { diaryRepository.insertProductDiaryEntry(productDiaryEntry = expectedProductDiaryEntry) } returns Resource.Success(expectedProductDiaryEntry)
        coEvery { offlineDiaryRepository.insertProduct(product = MockConstants.Diary.getSampleProduct()) } returns Resource.Success(Unit)
        coEvery { offlineDiaryRepository.insertProductDiaryEntry(expectedProductDiaryEntry) } returns Resource.Success(Unit)
        assertIs<Resource.Success<Unit>>(callTestedMethod())
        coVerify(exactly = 1) { diaryRepository.insertProductDiaryEntry(productDiaryEntry = expectedProductDiaryEntry) }
        coVerify(exactly = 1) { offlineDiaryRepository.insertProduct(MockConstants.Diary.getSampleProduct()) }
        coVerify(exactly = 1) { offlineDiaryRepository.insertProductDiaryEntry(productDiaryEntry = expectedProductDiaryEntry) }
    }

    private suspend fun callTestedMethod(
        product: Product = MockConstants.Diary.getSampleProduct(),
        weight: String = MockConstants.Diary.CORRECT_PRODUCT_DIARY_ENTRY_WEIGHT_1,
        date: LocalDate = MockConstants.getDate2021()
    ) = insertProductDiaryEntryUseCase(
        product = product,
        weightStringValue = weight,
        date = date,
        mealName = MealName.BREAKFAST
    )
}