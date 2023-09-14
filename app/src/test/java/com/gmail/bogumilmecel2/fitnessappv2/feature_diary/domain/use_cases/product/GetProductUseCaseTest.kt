package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertIs

class GetProductUseCaseTest {

    private val diaryRepository = mockk<DiaryRepository>()
    private val getProductUseCase = GetProductUseCase(diaryRepository = diaryRepository)

    @Test
    fun `Check if cached product is not null, resource success with cached product is returned`() =
        runTest {
            mockData()
            assertIs<Resource.Success<Product?>>(callTestedMethod())
            verify(expectedOnline = 0)
        }

    @Test
    fun `Check if cached product is null and online repository return resource success, resource success with cached product is returned`() =
        runTest {
            mockData(getOfflineProductResource = Resource.Success(data = null))
            assertIs<Resource.Success<Product?>>(callTestedMethod())
            verify()
        }

    @Test
    fun `Check if cached product is null and online repository return resource error, resource error with cached product is returned`() =
        runTest {
            mockData(
                getOfflineProductResource = Resource.Success(data = null),
                getProductResource = Resource.Error()
            )
            assertIs<Resource.Error<Product?>>(callTestedMethod())
            verify()
        }

    private fun verify(expectedOffline: Int = 1, expectedOnline: Int = 1) {
        coVerify(exactly = expectedOnline) { diaryRepository.getProduct(productId = MockConstants.Diary.PRODUCT_ID_11) }
        coVerify(exactly = expectedOffline) { diaryRepository.getOfflineProduct(productId = MockConstants.Diary.PRODUCT_ID_11) }
    }

    private fun mockData(
        getOfflineProductResource: Resource<Product?> = Resource.Success(data = MockConstants.Diary.getSampleProduct()),
        getProductResource: Resource<Product?> = Resource.Success(data = MockConstants.Diary.getSampleProduct())
    ) {
        coEvery { diaryRepository.getOfflineProduct(MockConstants.Diary.PRODUCT_ID_11) } returns getOfflineProductResource
        coEvery { diaryRepository.getProduct(MockConstants.Diary.PRODUCT_ID_11) } returns getProductResource
    }

    private suspend fun callTestedMethod() = getProductUseCase(productId = MockConstants.Diary.PRODUCT_ID_11)
}