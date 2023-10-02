package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseMockkTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.CORRECT_PAGE
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants.Diary.PRODUCT_NAME_1
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProductsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class SearchForProductsUseCaseTest : BaseMockkTest() {

    private val diaryRepository = mockk<DiaryRepository>()
    private val searchForProductsUseCase =
        SearchForProductsUseCase(diaryRepository = diaryRepository)

    @Test
    fun `if called with proper text and repository returns resource success, return resource success`() =
        runTest {
            mockRepository()
            callTestedMethod().assertIsSuccess()
            verifyRepositoryCall(exactly = 1)
        }

    @Test
    fun `if called with proper text and repository returns resource error, return resource error`() =
        runTest {
            mockRepository(repositoryResource = Resource.Error())
            callTestedMethod().assertIsError()
            verifyRepositoryCall(exactly = 1)
        }

    @Test
    fun `if called with empty search text, return resource error`() = runTest {
        mockRepository()
        callTestedMethod(searchText = "").assertIsError()
        verifyRepositoryCall(exactly = 0)
    }

    @Test
    fun `if called with null search text, return resource error`() = runTest {
        mockRepository()
        callTestedMethod(searchText = null).assertIsError()
        verifyRepositoryCall(exactly = 0)
    }

    private fun verifyRepositoryCall(exactly: Int = 1) {
        coVerify(exactly = exactly) {
            diaryRepository.searchForProducts(
                searchText = PRODUCT_NAME_1,
                page = CORRECT_PAGE
            )
        }
    }

    private fun mockRepository(repositoryResource: Resource<List<Product>> = Resource.Success(emptyList())) {
        coEvery {
            diaryRepository.searchForProducts(
                searchText = any(),
                page = any()
            )
        } returns repositoryResource
    }

    private suspend fun callTestedMethod(
        searchText: String? = PRODUCT_NAME_1,
        page: Int = CORRECT_PAGE
    ) = searchForProductsUseCase(
        searchText = searchText,
        page = page
    )
}