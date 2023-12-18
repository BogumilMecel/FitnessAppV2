package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetRecipeUseCaseTest: BaseTest() {

    private val diaryRepository = mockk<DiaryRepository>()
    private val getRecipeUseCase = GetRecipeUseCase(diaryRepository = diaryRepository)

    @Test
    fun `Check if cached product is not null, resource success with cached product is returned`() =
        runTest {
            mockData()
            callTestedMethod().assertIsSuccess()
            verify(expectedOnline = 0)
        }

    @Test
    fun `Check if cached product is null and online repository return resource success, resource success with cached product is returned`() =
        runTest {
            mockData(getOfflineRecipeResource = Resource.Success(data = null))
            callTestedMethod().assertIsSuccess()
            verify()
        }

    @Test
    fun `Check if cached product is null and online repository return resource error, resource error with cached product is returned`() =
        runTest {
            mockData(
                getOfflineRecipeResource = Resource.Success(data = null),
                getRecipeResource = Resource.Error()
            )
            callTestedMethod().assertIsError()
            verify()
        }

    private fun verify(expectedOffline: Int = 1, expectedOnline: Int = 1) {
        coVerify(exactly = expectedOnline) { diaryRepository.getRecipe(recipeId = MockConstants.Diary.RECIPE_ID_1) }
        coVerify(exactly = expectedOffline) { diaryRepository.getOfflineRecipe(recipeId = MockConstants.Diary.RECIPE_ID_1) }
    }

    private fun mockData(
        getOfflineRecipeResource: Resource<Recipe?> = Resource.Success(data = MockConstants.Diary.getSampleRecipe()),
        getRecipeResource: Resource<Recipe?> = Resource.Success(data = MockConstants.Diary.getSampleRecipe())
    ) {
        coEvery { diaryRepository.getOfflineRecipe(MockConstants.Diary.RECIPE_ID_1) } returns getOfflineRecipeResource
        coEvery { diaryRepository.getRecipe(MockConstants.Diary.RECIPE_ID_1) } returns getRecipeResource
    }

    private suspend fun callTestedMethod() = getRecipeUseCase(recipeId = MockConstants.Diary.RECIPE_ID_1)
}