package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.UserDiaryItemsResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertIs

class GetProductsAndSaveOfflineUseCaseTest: BaseTest() {
    private val diaryRepository = mockk<DiaryRepository>()
    private val getProductsAndSaveOfflineUseCase = GetProductsAndSaveOfflineUseCase(
        diaryRepository = diaryRepository,
        cachedValuesProvider = cachedValuesProvider
    )

    @Test
    fun `Check if diary repository returns resource error when getting diary items, resource error is returned`() = runTest {
        mockGetItemsResource(Resource.Error())
        assertIs<Resource.Error<Unit>>(callTestedMethod())
    }

    @Test
    fun `Check if diary repository returns empty items, items are not saved`() = runTest {
        mockGetItemsResource()
        verifyInsertMethods(
            expected = 0,
            products = emptyList(),
            recipes = emptyList()
        )
        assertIs<Resource.Success<Unit>>(callTestedMethod())
    }

    @Test
    fun `Check if diary repository returns not empty items, items are saved`() = runTest {
        val products = listOf(Product())
        val recipes = listOf(Recipe())
        mockGetItemsResource(
            resource = Resource.Success(
                data = mockUserDiaryItemsResponse(
                    products = products,
                    recipes = recipes
                )
            )
        )
        coEvery { diaryRepository.insertUserProductsLocally(products) } returns Resource.Success(Unit)
        coEvery { diaryRepository.insertUserRecipesLocally(recipes) } returns Resource.Success(Unit)
        assertIs<Resource.Success<Unit>>(callTestedMethod())
        verifyInsertMethods(
            expected = 1,
            products = products,
            recipes = recipes
        )
    }

    private fun verifyInsertMethods(
        products: List<Product>,
        recipes: List<Recipe>,
        expected: Int
    ) {
        coVerify(exactly = expected) {
            diaryRepository.insertUserProductsLocally(products)
            diaryRepository.insertUserRecipesLocally(recipes)
        }
    }

    private fun mockGetItemsResource(
        resource: Resource<UserDiaryItemsResponse> = Resource.Success(
            data = mockUserDiaryItemsResponse(
                products = emptyList(),
                recipes = emptyList()
            )
        )
    ) {
        coEvery { diaryRepository.getUserDiaryItems() } returns resource
    }

    private fun mockUserDiaryItemsResponse(
        products: List<Product>,
        recipes: List<Recipe>
    ) = UserDiaryItemsResponse(
        userProducts = products,
        userRecipes = recipes
    )

    private suspend fun callTestedMethod() = getProductsAndSaveOfflineUseCase()
}