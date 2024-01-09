package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.plusDays
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetProductsAndSaveOfflineUseCaseTest : BaseTest() {
    private val diaryRepository = mockk<DiaryRepository>()
    private val offlineDiaryRepository = mockk<OfflineDiaryRepository>()
    private val getProductsAndSaveOfflineUseCase = GetProductsAndSaveOfflineUseCase(
        diaryRepository = diaryRepository,
        cachedValuesProvider = cachedValuesProvider,
        offlineDiaryRepository = offlineDiaryRepository
    )

    @Test
    fun `Check if user is in offline mode resource error is returned`() = runTest {
        mockData(online = false)
        callTestedMethod().assertIsError()
    }

    @Test
    fun `Check if offline diary repository returns resource error when getting latest product, resource error is returned`() =
        runTest {
            mockData(getOfflineProductsResource = Resource.Error())
            callTestedMethod().assertIsError()
        }

    @Test
    fun `Check if diary repository returns error when getting products, resource error is returned`() =
        runTest {
            mockData(getOnlineProductsResource = Resource.Error())
            callTestedMethod().assertIsError()
        }

    @Test
    fun `Check if offline diary repository returns resource error when inserting products, resource error is returned`() =
        runTest {
            mockData(insertingResource = Resource.Error())
            callTestedMethod().assertIsError()
        }

    @Test
    fun `Check if offline diary repository returns resource success when inserting products, resource success is returned`() =
        runTest {
            mockData()
            callTestedMethod().assertIsSuccess()
        }

    private fun mockData(
        online: Boolean = true,
        getOfflineProductsResource: Resource<List<Product>> = Resource.Success(createOfflineProductsList()),
        getOnlineProductsResource: Resource<List<Product>> = Resource.Success(createOnlineProductsList()),
        insertingResource: Resource<Unit> = Resource.Success(Unit)
    ) {
        mockUserId()
        mockOfflineMode(online = online)
        coEvery {
            offlineDiaryRepository.getProducts(
                limit = 1,
                userId = MockConstants.USER_ID
            )
        } returns getOfflineProductsResource
        coEvery { diaryRepository.getUserProducts(latestDateTime = MockConstants.getDateTime()) } returns getOnlineProductsResource
        getOnlineProductsResource.data?.let {
            coEvery { offlineDiaryRepository.insertProducts(it) } returns insertingResource
        }
    }

    private fun createOfflineProductsList() =
        listOf(Product(creationDateTime = MockConstants.getDateTime()))

    private fun createOnlineProductsList() = buildList {
        repeat(4) {
            add(
                Product(
                    creationDateTime = MockConstants.getDateTime().plusDays(it)
                )
            )
        }
    }

    private suspend fun callTestedMethod() = getProductsAndSaveOfflineUseCase()
}