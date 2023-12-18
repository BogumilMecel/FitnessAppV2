package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetRecipesAndSaveOfflineUseCaseTest : BaseTest() {
    private val diaryRepository = mockk<DiaryRepository>()
    private val offlineDiaryRepository = mockk<OfflineDiaryRepository>()
    private val getRecipesAndSaveOfflineUseCase = GetRecipesAndSaveOfflineUseCase(
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
    fun `Check if offline diary repository returns resource error when getting latest recipe, resource error is returned`() =
        runTest {
            mockData(getOfflineRecipesResource = Resource.Error())
            callTestedMethod().assertIsError()
        }

    @Test
    fun `Check if diary repository returns error when getting recipes, resource error is returned`() =
        runTest {
            mockData(getOnlineRecipesResource = Resource.Error())
            callTestedMethod().assertIsError()
        }

    @Test
    fun `Check if offline diary repository returns resource error when inserting recipes, resource error is returned`() =
        runTest {
            mockData(insertingResource = Resource.Error())
            callTestedMethod().assertIsError()
        }

    @Test
    fun `Check if offline diary repository returns resource success when inserting recipes, resource success is returned`() =
        runTest {
            mockData()
            callTestedMethod().assertIsSuccess()
        }

    private fun mockData(
        online: Boolean = true,
        getOfflineRecipesResource: Resource<List<Recipe>> = Resource.Success(createOfflineRecipesList()),
        getOnlineRecipesResource: Resource<List<Recipe>> = Resource.Success(createOnlineRecipesList()),
        insertingResource: Resource<Unit> = Resource.Success(Unit)
    ) {
        mockUserId()
        mockOfflineMode(online = online)
        coEvery {
            offlineDiaryRepository.getRecipes(
                limit = 1,
                userId = MockConstants.USER_ID
            )
        } returns getOfflineRecipesResource
        coEvery { diaryRepository.getUserRecipes(latestTimestamp = MockConstants.TIMESTAMP) } returns getOnlineRecipesResource
        getOnlineRecipesResource.data?.let {
            coEvery { offlineDiaryRepository.insertRecipes(it) } returns insertingResource
        }
    }

    private fun createOfflineRecipesList() = listOf(Recipe(utcTimestamp = MockConstants.TIMESTAMP))

    private fun createOnlineRecipesList() = buildList {
        repeat(4) {
            add(Recipe(utcTimestamp = MockConstants.TIMESTAMP * it))
        }
    }

    private suspend fun callTestedMethod() = getRecipesAndSaveOfflineUseCase()
}