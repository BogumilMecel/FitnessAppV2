package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.mockito.Mock

@OptIn(ExperimentalCoroutinesApi::class)
internal class SaveProductToHistoryTest {

    @Mock
    private lateinit var mockDiaryRepository: DiaryRepository

    private lateinit var closable: AutoCloseable

//    @Before
//    fun setUp() = runTest {
//        closable = MockitoAnnotations.openMocks(this)
//        mockDiaryRepository = Mockito.mock(DiaryRepository::class.java)
//        Mockito.`when`(mockDiaryRepository.saveProductToHistory(any())).thenReturn(CustomResult.Success)
//        saveProductToHistory = SaveProductToHistory(
//            diaryRepository = mockDiaryRepository,
//        )
//    }
//
//    @Test
//    fun saveProduct_ResultSuccess() = runTest{
//        val result = saveProductToHistory(ProductWithId(productId = "", product = Product()))
//        assert(result is CustomResult.Success)
//    }

    @After
    fun after(){
        closable.close()
    }
}