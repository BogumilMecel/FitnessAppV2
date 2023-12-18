package com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.ActivityLevel
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.DesiredWeight
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.Gender
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.TrainingFrequency
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.TypeOfWork
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.repository.UserDataRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.any
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
internal class SaveIntroductionInformationUseCaseTest : BaseTest() {

    private lateinit var saveIntroductionInformationUseCase: SaveIntroductionInformationUseCase

    @Mock
    private lateinit var mockUserDataRepository: UserDataRepository

    @Before
    fun setUp() = runTest {
        mockUserDataRepository = Mockito.mock(UserDataRepository::class.java)
        mockRepositoryResponse()

        saveIntroductionInformationUseCase = SaveIntroductionInformationUseCase(
            resourceProvider = resourceProvider,
            userDataRepository = mockUserDataRepository
        )
    }

    @Test
    fun `if called with empty weight, return resource error`() = runTest {
        val result = callWithCorrectValues(weight = "")
        assertTrue(result is Resource.Error)
    }

    @Test
    fun `if called with weight less than 0, return resource error`() = runTest {
        val result = callWithCorrectValues(weight = "-1")
        assertTrue(result is Resource.Error)
    }

    @Test
    fun `if called with weight higher than 500, return resource error`() = runTest {
        val result = callWithCorrectValues(weight = "501")
        assertTrue(result is Resource.Error)
    }

    @Test
    fun `if called with incorrect weight, return resource error`() = runTest {
        val result = callWithCorrectValues(weight = "abc")
        assertTrue(result is Resource.Error)
    }

    @Test
    fun `if called with empty height, return resource error`() = runTest {
        val result = callWithCorrectValues(height = "")
        assertTrue(result is Resource.Error)
    }

    @Test
    fun `if called with height less than 0, return resource error`() = runTest {
        val result = callWithCorrectValues(height = "-1")
        assertTrue(result is Resource.Error)
    }

    @Test
    fun `if called with height higher than 250, return resource error`() = runTest {
        val result = callWithCorrectValues(height = "251")
        assertTrue(result is Resource.Error)
    }

    @Test
    fun `if called with incorrect height, return resource error`() = runTest {
        val result = callWithCorrectValues(height = "abc")
        assertTrue(result is Resource.Error)
    }

    @Test
    fun `if called with empty age, return resource error`() = runTest {
        val result = callWithCorrectValues(age = "")
        assertTrue(result is Resource.Error)
    }

    @Test
    fun `if called with age less than 0, return resource error`() = runTest {
        val result = callWithCorrectValues(age = "-1")
        assertTrue(result is Resource.Error)
    }

    @Test
    fun `if called with age higher than 100, return resource error`() = runTest {
        val result = callWithCorrectValues(age = "101")
        assertTrue(result is Resource.Error)
    }

    @Test
    fun `if called with incorrect age, return resource error`() = runTest {
        val result = callWithCorrectValues(age = "abc")
        assertTrue(result is Resource.Error)
    }

    @Test
    fun `if called with proper data and repository return resource success, return resource success`() = runTest {
        val result = callWithCorrectValues()
        assertTrue(result is Resource.Success)
    }

    @Test
    fun `if called with proper data and repository return resource error, return resource error`() = runTest {
        mockRepositoryResponse(response = Resource.Error())
        val result = callWithCorrectValues()
        assertTrue(result is Resource.Error)
    }

    private suspend fun mockRepositoryResponse(
        response: Resource<User> = Resource.Success(data = User())
    ) {
        Mockito.`when`(mockUserDataRepository.saveUserInformation(any())).thenReturn(response)
    }

    private suspend fun callWithCorrectValues(
        selectedGender: Gender = Gender.MALE,
        age: String = "18",
        height: String = "180",
        weight: String = "90",
        typeOfWork: TypeOfWork = TypeOfWork.MIXED,
        activityLevel: ActivityLevel = ActivityLevel.MODERATE,
        trainingFrequency: TrainingFrequency = TrainingFrequency.AVERAGE,
        desiredWeight: DesiredWeight = DesiredWeight.KEEP
    ): Resource<User> = saveIntroductionInformationUseCase(
            selectedGender = selectedGender,
            age = age,
            height = height,
            weight = weight,
            typeOfWork = typeOfWork,
            activityLevel = activityLevel,
            trainingFrequency = trainingFrequency,
            desiredWeight = desiredWeight
        )
}