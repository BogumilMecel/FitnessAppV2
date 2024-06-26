package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.mockito.Mock

@OptIn(ExperimentalCoroutinesApi::class)
internal class AddProductDiaryEntryTest{

    @Mock
    private lateinit var mockDiaryRepository: DiaryRepository

    private lateinit var closable:AutoCloseable
    private lateinit var insertProductDiaryEntryUseCase: InsertProductDiaryEntryUseCase

//    @Before
//    fun setUp() = runTest{
//        closable = MockitoAnnotations.openMocks(this)
//        mockDiaryRepository = Mockito.mock(DiaryRepository::class.java)
//        Mockito.`when`(mockDiaryRepository.addProductDiaryEntry(any())).thenReturn(CustomResult.Success)
//        addDiaryEntry = AddDiaryEntry(
//            diaryRepository = mockDiaryRepository,
//            resourceProvider = ResourceProvider(RuntimeEnvironment.getApplication())
//        )
//    }
//
//    @Test
//    fun nullWeight_ResultError() = runTest{
//        val result = addDiaryEntry(
//            productWithId = ProductWithId(productId = "", product = Product()),
//            weight = null,
//            dateModel = DateModel(),
//            nutritionValues = NutritionValues(),
//            mealName = ""
//        )
//        assert(result is CustomResult.Error)
//    }
//
//    @Test
//    fun weight0_ResultError() = runTest{
//        val result = addDiaryEntry(
//            productWithId = ProductWithId(productId = "", product = Product()),
//            weight = 0,
//            dateModel = DateModel(),
//            nutritionValues = NutritionValues(),
//            mealName = ""
//        )
//        assert(result is CustomResult.Error)
//    }
//
//    @Test
//    fun correctData_ResultSuccess() = runTest {
//        val result = addDiaryEntry(
//            productWithId = ProductWithId(productId = "", product = Product()),
//            weight = 10,
//            dateModel = DateModel(),
//            nutritionValues = NutritionValues(),
//            mealName = ""
//        )
//        assert(result is CustomResult.Success)
//    }

    @After
    fun after(){
        closable.close()
    }
}