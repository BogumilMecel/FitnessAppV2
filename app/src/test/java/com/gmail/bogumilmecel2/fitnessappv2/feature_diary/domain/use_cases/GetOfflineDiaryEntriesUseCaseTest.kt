package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntriesResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.GetOfflineDiaryEntriesUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import java.util.Date
import kotlin.test.assertTrue

internal class GetOfflineDiaryEntriesUseCaseTest {

    @Mock
    private lateinit var mockDiaryRepository: DiaryRepository

    private lateinit var getOfflineDiaryEntriesUseCase: GetOfflineDiaryEntriesUseCase

    @Before
    fun setUp() {
        mockDiaryRepository = Mockito.mock(DiaryRepository::class.java)
        getOfflineDiaryEntriesUseCase = GetOfflineDiaryEntriesUseCase(diaryRepository = mockDiaryRepository)
    }

    @Test
    fun `if called with proper date and repository returns resource success, return resource success`() =
        runTest {
            Mockito.`when`(mockDiaryRepository.getDiaryEntries(Date(System.currentTimeMillis()).toString()))
                .thenReturn(
                    Resource.Success(
                        data = DiaryEntriesResponse()
                    )
                )

            val result = getOfflineDiaryEntriesUseCase(date = Date(System.currentTimeMillis()).toString())

            assertTrue(result is Resource.Success)
        }

    @Test
    fun `if called with proper date and repository returns resource error, return resource error`() =
        runTest {
            Mockito.`when`(mockDiaryRepository.getDiaryEntries(Date(System.currentTimeMillis()).toString()))
                .thenReturn(
                    Resource.Error(
                        uiText = "abc"
                    )
                )

            val result = getOfflineDiaryEntriesUseCase(date = Date(System.currentTimeMillis()).toString())

            assertTrue(result is Resource.Error)
        }
}