package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.Test
import kotlin.test.assertIs

class CheckIfShouldShowWeightPickerUseCaseTest : BaseTest() {

    val checkIfShouldShowWeightPickerUseCase =
        CheckIfShouldShowWeightPickerUseCase(cachedValuesProvider)

    @Test
    fun `Check if dialogs was shown today, resource error is returned`() = runTest {
        mockData(lastTimeShowedWeightPicker = MockConstants.getDate2021())
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
            mockData(user = mockUser(latestWeightEntryDate = MockConstants.getDate2021()))
            assertIs<Resource.Error<Unit>>(checkIfShouldShowWeightPickerUseCase())
        }

    @Test
    fun `Check if data is correct, resource success is returned`() =
        runTest {
            mockData()
            assertIs<Resource.Success<Unit>>(checkIfShouldShowWeightPickerUseCase())
            coVerify(exactly = 1) {
                cachedValuesProvider.setLocalLastTimeShowedWeightPicker(MockConstants.getDate2021())
            }
        }

    private fun mockData(
        currentDate: LocalDate = MockConstants.getDate2021(),
        user: User = mockUser(),
        lastTimeShowedWeightPicker: LocalDate = MockConstants.getDate2022(),
    ) {
        mockDate(date = currentDate)
        coEvery { cachedValuesProvider.getLocalLastTimeShowedWeightPicker() } returns lastTimeShowedWeightPicker
        coEvery { cachedValuesProvider.getUser() } returns user
        coEvery { cachedValuesProvider.setLocalLastTimeShowedWeightPicker(currentDate) } returns Unit
    }

    private fun mockUser(
        askForWeightDaily: Boolean? = true,
        latestWeightEntryDate: LocalDate = MockConstants.getDate2022()
    ) = User(
        askForWeightDaily = askForWeightDaily,
        latestWeightEntry = WeightEntry(date = latestWeightEntryDate)
    )
}