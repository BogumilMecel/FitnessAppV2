package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetProductDiaryAndSaveOfflineUseCaseTest : BaseTest() {
    private val diaryRepository = mockk<DiaryRepository>()
    private val offlineDiaryRepository = mockk<OfflineDiaryRepository>()
    private val getProductDiaryAndSaveOfflineUseCase = GetProductDiaryAndSaveOfflineUseCase(
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
    fun `Check if offline diary repository returns resource error when getting latest product diary entry, resource error is returned`() =
        runTest {
            mockData(getOfflineProductDiaryEntriesResource = Resource.Error())
            callTestedMethod().assertIsError()
        }

    @Test
    fun `Check if diary repository returns error when getting product diary entries, resource error is returned`() =
        runTest {
            mockData(getOnlineProductDiaryEntriesResource = Resource.Error())
            callTestedMethod().assertIsError()
        }

    @Test
    fun `Check if offline diary repository returns resource error when inserting product diary entries, resource error is returned`() =
        runTest {
            mockData(insertingResource = Resource.Error())
            callTestedMethod().assertIsError()
        }

    @Test
    fun `Check if offline diary repository returns resource success when inserting product diary entries, resource success is returned`() =
        runTest {
            mockData()
            callTestedMethod().assertIsSuccess()
        }

    private fun mockData(
        online: Boolean = true,
        getOfflineProductDiaryEntriesResource: Resource<List<ProductDiaryEntry>> = Resource.Success(createOfflineProductDiaryEntriesList()),
        getOnlineProductDiaryEntriesResource: Resource<List<ProductDiaryEntry>> = Resource.Success(createOnlineProductDiaryEntriesList()),
        insertingResource: Resource<Unit> = Resource.Success(Unit)
    ) {
        mockUserId()
        mockOfflineMode(online = online)
        coEvery { offlineDiaryRepository.getProductDiaryEntries(limit = 1) } returns getOfflineProductDiaryEntriesResource
        coEvery { diaryRepository.getProductDiaryEntries(latestTimestamp = MockConstants.TIMESTAMP) } returns getOnlineProductDiaryEntriesResource
        getOnlineProductDiaryEntriesResource.data?.let {
            coEvery { offlineDiaryRepository.insertProductDiaryEntries(it) } returns insertingResource
        }
    }

    private fun createOfflineProductDiaryEntriesList() =
        listOf(ProductDiaryEntry(utcTimestamp = MockConstants.TIMESTAMP))

    private fun createOnlineProductDiaryEntriesList() = buildList {
        repeat(4) {
            add(ProductDiaryEntry(utcTimestamp = MockConstants.TIMESTAMP * it))
        }
    }

    private suspend fun callTestedMethod() = getProductDiaryAndSaveOfflineUseCase()
}