package com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
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
import org.junit.Test
import kotlin.test.assertIs

internal class SaveIntroductionInformationUseCaseTest : BaseTest() {

    private val userDataRepository = mockk<UserDataRepository>()
    private val saveIntroductionInformationUseCase = SaveIntroductionInformationUseCase(
        userDataRepository = userDataRepository,
        resourceProvider = resourceProvider
    )

    @Test
    fun `if called with empty weight, return resource error with correct text`() = runTest {
        callWithCorrectValues(weight = "")
            .assertIsError(resourceProvider.getString(R.string.empty_fields_error))
    }

    @Test
    fun `if called with weight less than 0, return resource error with correct text`() = runTest {
        callWithCorrectValues(weight = "-1")
            .assertIsError(resourceProvider.getString(R.string.please_make_sure_you_have_entered_your_weight))
    }

    @Test
    fun `if called with weight higher than 500, return resource error with correct text`() =
        runTest {
            callWithCorrectValues(weight = "501")
                .assertIsError(resourceProvider.getString(R.string.please_make_sure_you_have_entered_your_weight))
        }

    @Test
    fun `if called with incorrect weight, return resource error with correct text`() = runTest {
        callWithCorrectValues(weight = "abc")
            .assertIsError(resourceProvider.getString(R.string.empty_fields_error))
    }

    @Test
    fun `if called with empty height, return resource error with correct text`() = runTest {
        callWithCorrectValues(height = "")
            .assertIsError(resourceProvider.getString(R.string.empty_fields_error))
    }

    @Test
    fun `if called with height less than 0, return resource error with correct text`() = runTest {
        callWithCorrectValues(height = "-1")
            .assertIsError(resourceProvider.getString(R.string.introduction_height_error))
    }

    @Test
    fun `if called with height higher than 250, return resource error with correct text`() =
        runTest {
            callWithCorrectValues(height = "251")
                .assertIsError(resourceProvider.getString(R.string.introduction_height_error))
        }

    @Test
    fun `if called with incorrect height, return resource error with correct text`() = runTest {
        callWithCorrectValues(height = "abc")
            .assertIsError(resourceProvider.getString(R.string.empty_fields_error))
    }

    @Test
    fun `if called with empty age, return resource error with correct text`() = runTest {
        callWithCorrectValues(age = "")
            .assertIsError(resourceProvider.getString(R.string.empty_fields_error))
    }

    @Test
    fun `if called with age less than 0, return resource error with correct text`() = runTest {
        callWithCorrectValues(age = "-1")
            .assertIsError(resourceProvider.getString(R.string.please_make_sure_you_have_entered_your_age))
    }

    @Test
    fun `if called with age higher than 100, return resource error with correct text`() = runTest {
        callWithCorrectValues(age = "101")
            .assertIsError(resourceProvider.getString(R.string.please_make_sure_you_have_entered_your_age))
    }

    @Test
    fun `if called with incorrect age, return resource error`() = runTest {
        callWithCorrectValues(age = "abc")
            .assertIsError(resourceProvider.getString(R.string.empty_fields_error))
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
            coVerify(exactly = 1) {
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