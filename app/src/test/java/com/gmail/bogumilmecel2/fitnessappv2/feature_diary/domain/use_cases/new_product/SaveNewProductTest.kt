package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_product

import com.gmail.bogumilmecel2.fitnessappv2.common.util.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary.SortDiaryEntriesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
internal class SaveNewProductTest {

    @Mock
    private lateinit var mockDiaryRepository: DiaryRepository

    private lateinit var closable: AutoCloseable
    private lateinit var saveNewProduct: SaveNewProduct
    private lateinit var sortDiaryEntriesUseCase: SortDiaryEntriesUseCase
    private lateinit var resourceProvider: ResourceProvider

//    @Before
//    fun setUp() = runTest{
//        closable = MockitoAnnotations.openMocks(this)
//        mockDiaryRepository = Mockito.mock(DiaryRepository::class.java)
//        sortDiaryEntriesUseCase = SortDiaryEntriesUseCase()
//        Mockito.`when`(mockDiaryRepository.saveNewProduct(any())).thenReturn(
//            CustomResult.Success
//        )
//        saveNewProduct = SaveNewProduct(
//            diaryRepository = mockDiaryRepository,
//            resourceProvider = ResourceProvider(RuntimeEnvironment.getApplication())
//        )
//    }

//    @Test
//    fun incorrectCalories_ResultError() = runTest{
//        val result = saveNewProduct(
//            name = "name",
//            containerWeight = "21",
//            position = 0,
//            unit = "g",
//            calories = "abc",
//            carbohydrates = "21",
//            protein = "21",
//            fat = "21",
//            barcode = ""
//        )
//        assert(result is CustomResult.Error)
//    }
//
//    @Test
//    fun incorrectCarbohydrates_ResultError() = runTest{
//        val result = saveNewProduct(
//            name = "name",
//            containerWeight = "21",
//            position = 0,
//            unit = "g",
//            calories = "21",
//            carbohydrates = "abc",
//            protein = "21.2",
//            fat = "21.2",
//            barcode = ""
//        )
//        assert(result is CustomResult.Error)
//    }
//
//    @Test
//    fun incorrectProtein_ResultError() = runTest{
//        val result = saveNewProduct(
//            name = "name",
//            containerWeight = "21",
//            position = 0,
//            unit = "g",
//            calories = "21",
//            carbohydrates = "21.2",
//            protein = "abc",
//            fat = "21.2",
//            barcode = ""
//        )
//        assert(result is CustomResult.Error)
//    }
//
//    @Test
//    fun incorrectFat_ResultError() = runTest{
//        val result = saveNewProduct(
//            name = "name",
//            containerWeight = "21",
//            position = 0,
//            unit = "g",
//            calories = "21",
//            carbohydrates = "21.2",
//            protein = "21.2",
//            fat = "abc",
//            barcode = ""
//        )
//        assert(result is CustomResult.Error)
//    }
//
//    @Test
//    fun blankName_ResultError() = runTest{
//        val result = saveNewProduct(
//            name = "",
//            containerWeight = "21",
//            position = 0,
//            unit = "g",
//            calories = "21",
//            carbohydrates = "21.2",
//            protein = "21.2",
//            fat = "21.2",
//            barcode = ""
//        )
//        assert(result is CustomResult.Error)
//    }
//
//    @Test
//    fun nameShortedThan4Characters_ResultError() = runTest{
//        val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
//        val randomString = (1..3)
//            .map { _ -> kotlin.random.Random.nextInt(0, charPool.size) }
//            .map(charPool::get)
//            .joinToString("")
//        println(randomString.length)
//        val result = saveNewProduct(
//            name = randomString,
//            containerWeight = "21",
//            position = 0,
//            unit = "g",
//            calories = "21",
//            carbohydrates = "21.2",
//            protein = "21.2",
//            fat = "21.2",
//            barcode = ""
//        )
//        assert(result is CustomResult.Error)
//    }
//
//    @Test
//    fun nameLongerThan24Characters_ResultError() = runTest{
//        val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
//        val randomString = (1..25)
//            .map { _ -> kotlin.random.Random.nextInt(0, charPool.size) }
//            .map(charPool::get)
//            .joinToString("")
//        val result = saveNewProduct(
//            name = randomString,
//            containerWeight = "21",
//            position = 0,
//            unit = "g",
//            calories = "21",
//            carbohydrates = "21.2",
//            protein = "21.2",
//            fat = "21.2",
//            barcode = ""
//        )
//        assert(result is CustomResult.Error)
//    }
//
//    @Test
//    fun positionDifferentThan0AndEmptyContainerWeight_ResultError() = runTest{
//        val result = saveNewProduct(
//            name = "name",
//            containerWeight = "",
//            position = 1,
//            unit = "g",
//            calories = "21",
//            carbohydrates = "21.2",
//            protein = "21.2",
//            fat = "21.2",
//            barcode = ""
//        )
//        assert(result is CustomResult.Error)
//    }
//
//    @Test
//    fun correctData_ResultSuccess() = runTest{
//        val result = saveNewProduct(
//            name = "name",
//            containerWeight = "21",
//            position = 1,
//            unit = "g",
//            calories = "21",
//            carbohydrates = "21.2",
//            protein = "21.2",
//            fat = "21.2",
//            barcode = ""
//        )
//        assert(result is CustomResult.Success)
//    }

    @After
    fun after() {
        closable.close()
    }
}