package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseMockkTest
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertIs

class CheckIfShouldShowWeightPickerUseCaseTest : BaseMockkTest() {

    val checkIfShouldShowWeightPickerUseCase =
        CheckIfShouldShowWeightPickerUseCase(cachedValuesProvider)

    @Test
    fun `Check if dialogs was shown today, resource error is returned`() = runTest {
        mockData(lastTimeShowedWeightPicker = mockedDate)
        assertIs<Resource.Error<Unit>>(checkIfShouldShowWeightPickerUseCase())
    }

    @Test
    fun `Check if weight dialogs are set to false, resource error is returned`() = runTest {
        mockData(user = mockUser(askForWeightDaily = false))
        assertIs<Resource.Error<Unit>>(checkIfShouldShowWeightPickerUseCase())
    }

    @Test
    fun `Check if weight dialogs are set to null, resource error is returned`() = runTest {
        mockData(user = mockUser(askForWeightDaily = null))
        assertIs<Resource.Error<Unit>>(checkIfShouldShowWeightPickerUseCase())
    }

    @Test
    fun `Check if weight entry has been already entered today, resource error is returned`() =
        runTest {
            mockData(user = mockUser(latestWeightEntryDate = mockedDate))
            assertIs<Resource.Error<Unit>>(checkIfShouldShowWeightPickerUseCase())
        }

    @Test
    fun `Check if data is correct, resource success is returned`() =
        runTest {
            mockData()
            assertIs<Resource.Success<Unit>>(checkIfShouldShowWeightPickerUseCase())
            coVerify(exactly = 1) {
                cachedValuesProvider.setLocalLastTimeShowedWeightPicker(mockedDate)
            }
        }

    private fun mockData(
        currentDate: String = mockedDate,
        user: User = mockUser(),
        lastTimeShowedWeightPicker: String = mockedDate2,
    ) {
        mockDateString(value = currentDate)
        coEvery { cachedValuesProvider.getLocalLastTimeShowedWeightPicker() } returns lastTimeShowedWeightPicker
        coEvery { cachedValuesProvider.getUser() } returns user
        coEvery { cachedValuesProvider.setLocalLastTimeShowedWeightPicker(currentDate) } returns Unit
    }

    private fun mockUser(
        askForWeightDaily: Boolean? = true,
        latestWeightEntryDate: String = mockedDate2
    ) = User(
        askForWeightDaily = askForWeightDaily,
        latestWeightEntry = WeightEntry(date = latestWeightEntryDate)
    )
}