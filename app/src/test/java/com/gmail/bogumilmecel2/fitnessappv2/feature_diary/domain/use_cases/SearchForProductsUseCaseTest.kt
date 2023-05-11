package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.GetDiaryHistory
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search.SearchForProductsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
internal class SearchForProductsUseCaseTest{

    @Mock
    private lateinit var mockDiaryRepository: DiaryRepository

    private lateinit var searchForProductsUseCase: SearchForProductsUseCase

    @Before
    fun setUp() = runTest{
        mockDiaryRepository = Mockito.mock(DiaryRepository::class.java)
        searchForProductsUseCase = SearchForProductsUseCase(
            diaryRepository = mockDiaryRepository,
            getDiaryHistory = GetDiaryHistory(mockDiaryRepository)
        )
    }

    @Test
    fun `if called with proper text and repository returns resource success, return resource success`() = runTest{
        mockRepositoryResponse(response = Resource.Success(data = emptyList()))

        val result = searchForProductsUseCase(searchText = "abc")
        assertTrue(result is Resource.Success)
    }

    @Test
    fun `if called with proper text and repository returns resource error, return resource error`() = runTest{
        val errorText = "error text"
        mockRepositoryResponse(response = Resource.Error(uiText = errorText))

        val result = searchForProductsUseCase(searchText = "abc")
        assertTrue(result is Resource.Error)
        assertEquals(
            expected = errorText,
            actual = result.uiText
        )
    }

    @Test
    fun `if called with empty search text, return resource error`() = runTest{
        val result = searchForProductsUseCase("")
        assertTrue(result is Resource.Error)
    }

    private suspend fun mockRepositoryResponse(response: Resource<List<Product>>) {
        Mockito.`when`(mockDiaryRepository.searchForProducts(Mockito.anyString())).thenReturn(
            Resource.Success(data = emptyList())
        )
    }
}