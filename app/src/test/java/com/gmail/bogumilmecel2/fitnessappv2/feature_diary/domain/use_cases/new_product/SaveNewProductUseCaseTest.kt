package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_product

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NutritionValuesIn
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.util.MockResourceProvider
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.any
import kotlin.random.Random

internal class SaveNewProductUseCaseTest {

    @Mock
    private lateinit var mockDiaryRepository: DiaryRepository

    private lateinit var saveNewProductUseCase: SaveNewProductUseCase

    @Before
    fun setUp() = runTest {
        mockDiaryRepository = Mockito.mock(DiaryRepository::class.java)
        Mockito.`when`(mockDiaryRepository.saveNewProduct(any())).thenReturn(
            Resource.Success(
                data = Product()
            )
        )
        saveNewProductUseCase = SaveNewProductUseCase(
            diaryRepository = mockDiaryRepository,
            resourceProvider = MockResourceProvider()
        )
    }

    @Test
    fun `if called with incorrect calories string value, return resource error`() = runTest {
        val result = callSaveNewProductUseCaseWithCorrectDefaultValues(calories = "abc")
        assert(result is Resource.Error)
    }

    @Test
    fun `if called with incorrect carbohydrates string value, return resource error`() = runTest {
        val result = callSaveNewProductUseCaseWithCorrectDefaultValues(carbohydrates = "abc")
        assert(result is Resource.Error)
    }

    @Test
    fun `if called with incorrect protein string value, return resource error`() = runTest {
        val result = callSaveNewProductUseCaseWithCorrectDefaultValues(protein = "abc")
        assert(result is Resource.Error)
    }

    @Test
    fun `if called with incorrect fat string value, return resource error`() = runTest {
        val result = callSaveNewProductUseCaseWithCorrectDefaultValues(fat = "abc")
        assert(result is Resource.Error)
    }

    @Test
    fun `if called with blank name, return resource error`() = runTest {
        val result = callSaveNewProductUseCaseWithCorrectDefaultValues(name = "")
        assert(result is Resource.Error)
    }

    @Test
    fun `if called with name shorter than 4 characters, return resource error`() = runTest {
        val result = callSaveNewProductUseCaseWithCorrectDefaultValues(name = "abc")
        assert(result is Resource.Error)
    }

    @Test
    fun `if called with name longer than 48 characters, return resource error`() = runTest {
        val chars = ('a'..'z') + ('A'..'Z')
        val result = callSaveNewProductUseCaseWithCorrectDefaultValues(
            name = (1..49)
                .map { Random.nextInt(from = 0, until = chars.size) }
                .map(chars::get)
                .joinToString { "" }
        )
        assert(result is Resource.Error)
    }

    @Test
    fun `if called with nutrition values in average and container weight is incorrect return resource error`() = runTest {
        val result = callSaveNewProductUseCaseWithCorrectDefaultValues(
            containerWeight = "abc",
            nutritionValuesIn = NutritionValuesIn.AVERAGE
        )
        assert(result is Resource.Error)
    }

    @Test
    fun `if called with nutrition values in average and container weight is blank, return resource error`() = runTest {
        val result = callSaveNewProductUseCaseWithCorrectDefaultValues(
            containerWeight = "",
            nutritionValuesIn = NutritionValuesIn.AVERAGE
        )
        assert(result is Resource.Error)
    }

    @Test
    fun `if called with nutrition values in average and container weight is correct, return resource success`() = runTest {
        val result = callSaveNewProductUseCaseWithCorrectDefaultValues(
            containerWeight = "50",
            nutritionValuesIn = NutritionValuesIn.AVERAGE
        )
        assert(result is Resource.Success)
    }

    @Test
    fun `if called with nutrition values in container and container weight is incorrect, return resource error`() = runTest {
        val result = callSaveNewProductUseCaseWithCorrectDefaultValues(
            containerWeight = "abc",
            nutritionValuesIn = NutritionValuesIn.CONTAINER
        )
        assert(result is Resource.Error)
    }

    @Test
    fun `if called with nutrition values in container and container weight is blank, return resource error`() = runTest {
        val result = callSaveNewProductUseCaseWithCorrectDefaultValues(
            containerWeight = "",
            nutritionValuesIn = NutritionValuesIn.CONTAINER
        )
        assert(result is Resource.Error)
    }

    @Test
    fun `if called with nutrition values in container and container weight is correct, return resource success`() = runTest {
        val result = callSaveNewProductUseCaseWithCorrectDefaultValues(
            containerWeight = "21",
            nutritionValuesIn = NutritionValuesIn.CONTAINER
        )
        assert(result is Resource.Success)
    }

    @Test
    fun `if called with nutrition values in 100 grams and container weight is blank, return resource success`() = runTest {
        val result = callSaveNewProductUseCaseWithCorrectDefaultValues(
            containerWeight = "",
            nutritionValuesIn = NutritionValuesIn.HUNDRED_GRAMS
        )
        assert(result is Resource.Success)
    }

    @Test
    fun `if called with nutrition values in 100 grams and container weight is incorrect, return resource error`() = runTest {
        val result = callSaveNewProductUseCaseWithCorrectDefaultValues(
            containerWeight = "abc",
            nutritionValuesIn = NutritionValuesIn.HUNDRED_GRAMS
        )
        assert(result is Resource.Error)
    }

    @Test
    fun `if called with correct data, return resource success`() = runTest {
        val result = callSaveNewProductUseCaseWithCorrectDefaultValues()
        assert(result is Resource.Success)
    }

    private suspend fun callSaveNewProductUseCaseWithCorrectDefaultValues(
        name: String = "name",
        containerWeight: String = "200",
        nutritionValuesIn: NutritionValuesIn = NutritionValuesIn.AVERAGE,
        measurementUnit: MeasurementUnit = MeasurementUnit.GRAMS,
        calories: String = "215",
        carbohydrates: String = "21",
        protein: String = "17",
        fat: String = "5",
        barcode: String = "1234567890",
    ): Resource<Product> = saveNewProductUseCase(
        name = name,
        containerWeight = containerWeight,
        nutritionValuesIn = nutritionValuesIn,
        measurementUnit = measurementUnit,
        calories = calories,
        carbohydrates = carbohydrates,
        protein = protein,
        fat = fat,
        barcode = barcode
    )
}