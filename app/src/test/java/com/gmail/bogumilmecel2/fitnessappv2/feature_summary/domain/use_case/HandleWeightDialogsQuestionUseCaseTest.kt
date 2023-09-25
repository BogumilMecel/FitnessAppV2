package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseMockkTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.WeightDialogsQuestion
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockkClass
import kotlinx.coroutines.test.runTest
import org.junit.Test

class HandleWeightDialogsQuestionUseCaseTest : BaseMockkTest() {

    private val saveAskForWeightDailyUseCase = mockkClass(SaveAskForWeightDailyUseCase::class)
    private val handleWeightDialogsQuestionUseCase = HandleWeightDialogsQuestionUseCase(
        cachedValuesProvider = cachedValuesProvider,
        saveAskForWeightDaily = saveAskForWeightDailyUseCase
    )

    @Test
    fun `Check if save return resource error, resource error is returned`() = runTest {
        mockData(responseResource = Resource.Error())
        handleWeightDialogsQuestionUseCase(accepted = true).assertIsError()
    }

    @Test
    fun `Check if accepted is null last time asked is updated and resource error is returned`() =
        runTest {
            val weightDialogsQuestion = WeightDialogsQuestion(
                lastTimeAsked = MockConstants.MOCK_DATE_2021,
                askedCount = 1
            )
            mockData(savedWeightDialogsQuestion = weightDialogsQuestion)
            handleWeightDialogsQuestionUseCase(accepted = null).assertIsError()
            coVerify(exactly = 1) { cachedValuesProvider.getLocalWeightDialogsQuestion() }
            coVerify(exactly = 1) {
                cachedValuesProvider.updateLocalWeightDialogsQuestion(
                    weightDialogsQuestion = weightDialogsQuestion.copy(
                        lastTimeAsked = mockedDate,
                        askedCount = weightDialogsQuestion.askedCount.plus(1)
                    )
                )
            }
        }

    @Test
    fun `Check if repository return resource success, resource success is returned and weight dialogs are cached`() =
        runTest {
            mockData()
            handleWeightDialogsQuestionUseCase(accepted = true).assertIsSuccess()
        }

    private fun mockData(
        responseResource: Resource<Unit> = Resource.Success(Unit),
        savedWeightDialogsQuestion: WeightDialogsQuestion? = null
    ) {
        mockDateString(mockedDate)
        coEvery {
            cachedValuesProvider.getLocalWeightDialogsQuestion()
        } returns savedWeightDialogsQuestion
        coEvery {
            cachedValuesProvider.updateLocalWeightDialogsQuestion(
                weightDialogsQuestion = any()
            )
        } returns Unit
        coEvery {
            saveAskForWeightDailyUseCase(
                accepted = any(),
                cachedValuesProvider = cachedValuesProvider
            )
        } returns responseResource
    }
}