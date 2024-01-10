package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.plusDays
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetRecipeDiaryAndSaveOfflineUseCaseTest : BaseTest() {
    private val diaryRepository = mockk<DiaryRepository>()
    private val offlineDiaryRepository = mockk<OfflineDiaryRepository>()
    private val getRecipeDiaryAndSaveOfflineUseCase = GetRecipeDiaryAndSaveOfflineUseCase(
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
    fun `Check if offline diary repository returns resource error when getting latest recipe diary entry, resource error is returned`() =
        runTest {
            mockData(getOfflineRecipeDiaryEntriesResource = Resource.Error())
            callTestedMethod().assertIsError()
        }

    @Test
    fun `Check if diary repository returns error when getting recipe diary entries, resource error is returned`() =
        runTest {
            mockData(getOnlineRecipeDiaryEntriesResource = Resource.Error())
            callTestedMethod().assertIsError()
        }

    @Test
    fun `Check if offline diary repository returns resource error when inserting recipe diary entries, resource error is returned`() =
        runTest {
            mockData(insertingResource = Resource.Error())
            callTestedMethod().assertIsError()
        }

    @Test
    fun `Check if offline diary repository returns resource success when inserting recipe diary entries, resource success is returned`() =
        runTest {
            mockData()
            callTestedMethod().assertIsSuccess()
        }

    private fun mockData(
        online: Boolean = true,
        getOfflineRecipeDiaryEntriesResource: Resource<List<RecipeDiaryEntry>> = Resource.Success(createOfflineRecipeDiaryEntriesList()),
        getOnlineRecipeDiaryEntriesResource: Resource<List<RecipeDiaryEntry>> = Resource.Success(createOnlineRecipeDiaryEntriesList()),
        insertingResource: Resource<Unit> = Resource.Success(Unit)
    ) {
        mockUserId()
        mockOfflineMode(online = online)
        coEvery { offlineDiaryRepository.getRecipeDiaryEntries(limit = 1) } returns getOfflineRecipeDiaryEntriesResource
        coEvery { diaryRepository.getRecipeDiaryEntries(latestDateTime = MockConstants.getDateTime()) } returns getOnlineRecipeDiaryEntriesResource
        getOnlineRecipeDiaryEntriesResource.data?.let {
            coEvery { offlineDiaryRepository.insertRecipeDiaryEntries(it) } returns insertingResource
        }
    }

    private fun createOfflineRecipeDiaryEntriesList() =
        listOf(RecipeDiaryEntry(changeDateTime = MockConstants.getDateTime()))

    private fun createOnlineRecipeDiaryEntriesList() = buildList {
        repeat(4) {
            add(RecipeDiaryEntry(creationDateTime = MockConstants.getDateTime().plusDays(it)))
        }
    }

    private suspend fun callTestedMethod() = getRecipeDiaryAndSaveOfflineUseCase()
}