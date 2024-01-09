package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.WeightDialogsQuestion
import io.mockk.coEvery
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.Test

class CheckIfShouldAskForWeightDialogsUseCaseTest : BaseTest() {

    private val checkIfShouldAskForWeightDialogsUseCase = CheckIfShouldAskForWeightDialogsUseCase()

    @Test
    fun `check if user has already accepted weight dialogs, resource error is returned`() =
        runTest {
            mockData(askForWeightDaily = true)
            callTestedMethod().assertIsError()
        }

    @Test
    fun `check if user has already declined weight dialogs, resource error is returned`() =
        runTest {
            mockData(askForWeightDaily = false)
            callTestedMethod().assertIsError()
        }

    @Test
    fun `check if user has already seen weight dialogs, resource error is returned`() =
        runTest {
            mockData(weightDialogsQuestion = mockWeightDialogs(lastTimeAsked = MockConstants.getDate2021()))
            mockDate(MockConstants.getDate2021())
            callTestedMethod().assertIsError()
        }

    @Test
    fun `check if user has already been asked 3 times, resource error is returned`() =
        runTest {
            mockData(weightDialogsQuestion = mockWeightDialogs(askedCount = 4))
            callTestedMethod().assertIsError()
        }

    @Test
    fun `check if everything is correct, resource success is returned`() =
        runTest {
            mockData()
            mockDate(date = MockConstants.getDate2021())
            callTestedMethod().assertIsSuccess()
        }

    private fun mockData(
        askForWeightDaily: Boolean? = null,
        weightDialogsQuestion: WeightDialogsQuestion? = null
    ) {
        coEvery { cachedValuesProvider.getLocalWeightDialogsQuestion() } returns weightDialogsQuestion
        coEvery { cachedValuesProvider.getUser() } returns User(askForWeightDaily = askForWeightDaily)
    }

    private suspend fun callTestedMethod() =
        checkIfShouldAskForWeightDialogsUseCase(cachedValuesProvider)

    private fun mockWeightDialogs(
        lastTimeAsked: LocalDate = MockConstants.getDate2022(),
        askedCount: Int = 3
    ) = WeightDialogsQuestion(
        lastTimeAsked = lastTimeAsked,
        askedCount = askedCount
    )
}