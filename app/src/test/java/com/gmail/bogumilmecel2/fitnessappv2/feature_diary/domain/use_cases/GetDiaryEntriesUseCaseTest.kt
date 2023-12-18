package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.util.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.GetDiaryEntriesUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.SortDiaryEntriesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
internal class GetDiaryEntriesUseCaseTest {

    @Mock
    private lateinit var mockDiaryRepository: DiaryRepository

    private lateinit var closable: AutoCloseable
    private lateinit var getDiaryEntriesUseCase: GetDiaryEntriesUseCase
    private lateinit var sortDiaryEntriesUseCase: SortDiaryEntriesUseCase
    private lateinit var resourceProvider: ResourceProvider

//    @Before
//    fun setUp() {
//        closable = MockitoAnnotations.openMocks(this)
//        mockDiaryRepository = Mockito.mock(DiaryRepository::class.java)
//        sortDiaryEntriesUseCase = SortDiaryEntriesUseCase()
//        getDiaryEntriesUseCase = GetDiaryEntriesUseCase(
//            diaryRepository = mockDiaryRepository,
//            sortDiaryEntriesUseCase = sortDiaryEntriesUseCase
//        )
//        resourceProvider = ResourceProvider(RuntimeEnvironment.getApplication())
//    }
//
//    @Test
//    fun `if called with proper date and repository returns resource success, return resource success`() =
//        runTest {
//            val diaryEntriesResponse = DiaryEntriesResponse(
//                productDiaryEntries = emptyList(),
//                recipeDiaryEntries = emptyList()
//            )
//
//            Mockito.`when`(mockDiaryRepository.getDiaryEntries(Date(System.currentTimeMillis()).toString()))
//                .thenReturn(
//                    Resource.Success(
//                        data = diaryEntriesResponse
//                    )
//                )
//
//            val result = getDiaryEntriesUseCase(date = Date(System.currentTimeMillis()).toString())
//
//            assertTrue(result is Resource.Success)
//            assertEquals(
//                expected = sortDiaryEntriesUseCase(
//                    diaryEntriesResponse = diaryEntriesResponse
//                ),
//                actual = result.data
//            )
//        }
//
//    @Test
//    fun `if called with proper date and repository returns resource error, return resource error`() =
//        runTest {
//            val errorMessage = "abc"
//
//            Mockito.`when`(mockDiaryRepository.getDiaryEntries(Date(System.currentTimeMillis()).toString()))
//                .thenReturn(
//                    Resource.Error(
//                        uiText = errorMessage
//                    )
//                )
//
//            val result = getDiaryEntriesUseCase(date = Date(System.currentTimeMillis()).toString())
//
//            assertTrue(result is Resource.Error)
//            assertEquals(
//                expected = errorMessage,
//                actual = result.uiText
//            )
//        }

    @After
    fun after() {
        closable.close()
    }
}