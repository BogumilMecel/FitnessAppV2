package com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.BaseMockkTest
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.ActivityLevel
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.DesiredWeight
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.Gender
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.IntroductionResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.TrainingFrequency
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.TypeOfWork
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.repository.UserDataRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

internal class SaveIntroductionInformationUseCaseTest : BaseMockkTest() {
    
    companion object {
        const val AGE_ERROR = "age error"
        const val FIELD_ERROR = "field error"
        const val HEIGHT_ERROR = "height error"
        const val WEIGHT_ERROR = "weight error"
    }

    private val userDataRepository = mockk<UserDataRepository>()
    private val saveIntroductionInformationUseCase = SaveIntroductionInformationUseCase(
        userDataRepository = userDataRepository,
        resourceProvider = resourceProvider
    )
    
    @Before
    fun setUp() {
        mockString(
            resId = R.string.please_make_sure_all_fields_are_filled_in_correctly,
            value = FIELD_ERROR
        )
        mockString(
            resId = R.string.please_make_sure_you_have_entered_your_age,
            value = AGE_ERROR
        )
        mockString(
            resId = R.string.introduction_height_error,
            value = HEIGHT_ERROR
        )
        mockString(
            resId = R.string.please_make_sure_you_have_entered_your_weight,
            value = WEIGHT_ERROR
        )
    }

    @Test
    fun `if called with empty weight, return resource error with correct text`() = runTest {
        val resource = callWithCorrectValues(weight = "")
        assertIs<Resource.Error<Unit>>(resource)
        assertEquals(
            expected = FIELD_ERROR,
            resource.uiText
        )
    }

    @Test
    fun `if called with weight less than 0, return resource error with correct text`() = runTest {
        val resource = callWithCorrectValues(weight = "-1")
        assertIs<Resource.Error<Unit>>(resource)
        assertEquals(
            expected = WEIGHT_ERROR,
            resource.uiText
        )
    }

    @Test
    fun `if called with weight higher than 500, return resource error with correct text`() = runTest {
        val resource = callWithCorrectValues(weight = "501")
        assertIs<Resource.Error<Unit>>(resource)
        assertEquals(
            expected = WEIGHT_ERROR,
            resource.uiText
        )
    }

    @Test
    fun `if called with incorrect weight, return resource error with correct text`() = runTest {
        val resource = callWithCorrectValues(weight = "abc")
        assertIs<Resource.Error<Unit>>(resource)
        assertEquals(
            expected = FIELD_ERROR,
            resource.uiText
        )
    }

    @Test
    fun `if called with empty height, return resource error with correct text`() = runTest {
        val resource = callWithCorrectValues(height = "")
        assertIs<Resource.Error<Unit>>(resource)
        assertEquals(
            expected = FIELD_ERROR,
            resource.uiText
        )
    }

    @Test
    fun `if called with height less than 0, return resource error with correct text`() = runTest {
        val resource = callWithCorrectValues(height = "-1")
        assertIs<Resource.Error<Unit>>(resource)
        assertEquals(
            expected = HEIGHT_ERROR,
            resource.uiText
        )
    }

    @Test
    fun `if called with height higher than 250, return resource error with correct text`() = runTest {
        val resource = callWithCorrectValues(height = "251")
        assertIs<Resource.Error<Unit>>(resource)
        assertEquals(
            expected = HEIGHT_ERROR,
            resource.uiText
        )
    }

    @Test
    fun `if called with incorrect height, return resource error with correct text`() = runTest {
        val resource = callWithCorrectValues(height = "abc")
        assertIs<Resource.Error<Unit>>(resource)
        assertEquals(
            expected = FIELD_ERROR,
            resource.uiText
        )
    }

    @Test
    fun `if called with empty age, return resource error with correct text`() = runTest {
        val resource = callWithCorrectValues(age = "")
        assertIs<Resource.Error<Unit>>(resource)
        assertEquals(
            expected = FIELD_ERROR,
            resource.uiText
        )
    }

    @Test
    fun `if called with age less than 0, return resource error with correct text`() = runTest {
        val resource = callWithCorrectValues(age = "-1")
        assertIs<Resource.Error<Unit>>(resource)
        assertEquals(
            expected = AGE_ERROR,
            resource.uiText
        )
    }

    @Test
    fun `if called with age higher than 100, return resource error with correct text`() = runTest {
        val resource = callWithCorrectValues(age = "101")
        assertIs<Resource.Error<Unit>>(resource)
        assertEquals(
            expected = AGE_ERROR,
            resource.uiText
        )
    }

    @Test
    fun `if called with incorrect age, return resource error`() = runTest {
        val resource = callWithCorrectValues(age = "abc")
        assertIs<Resource.Error<Unit>>(resource)
        assertEquals(
            expected = FIELD_ERROR,
            resource.uiText
        )
    }

    @Test
    fun `if called with proper data and repository return resource success, return resource success`() =
        runTest {
            val nutritionValues = NutritionValues()
            val userInformation = UserInformation()
            coEvery { userDataRepository.saveUserInformation(any()) } returns Resource.Success(
                data = IntroductionResponse(
                    nutritionValues = nutritionValues,
                    userInformation = userInformation
                )
            )
            coEvery { cachedValuesProvider.saveWantedNutritionValues(nutritionValues) } returns Unit
            coEvery { cachedValuesProvider.updateUserInformation(userInformation) } returns Unit
            assertIs<Resource.Success<Unit>>(callWithCorrectValues())
            coVerify(exactly = 1){
                cachedValuesProvider.saveWantedNutritionValues(nutritionValues)
                cachedValuesProvider.updateUserInformation(userInformation)
            }
        }

    @Test
    fun `if called with proper data and repository return resource error, return resource error`() =
        runTest {
            coEvery { userDataRepository.saveUserInformation(any()) } returns Resource.Error()
            assertIs<Resource.Error<Unit>>(callWithCorrectValues())
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
    ) = saveIntroductionInformationUseCase(
        selectedGender = selectedGender,
        age = age,
        height = height,
        weight = weight,
        typeOfWork = typeOfWork,
        activityLevel = activityLevel,
        trainingFrequency = trainingFrequency,
        desiredWeight = desiredWeight,
        cachedValuesProvider = cachedValuesProvider
    )
}