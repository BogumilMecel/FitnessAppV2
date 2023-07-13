package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseMockkTest
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightDialogs
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository.WeightRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertIs

class HandleWeightDialogsQuestionUseCaseTest: BaseMockkTest() {

    private val weightRepository = mockk<WeightRepository>()
    val handleWeightDialogsQuestionUseCase = HandleWeightDialogsQuestionUseCase(
        weightRepository = weightRepository,
        cachedValuesProvider = cachedValuesProvider
    )

    @Test
    fun `Check if repository return resource error, resource error is returned`() = runTest {
        mockData(responseResource = Resource.Error())
        assertIs<Resource.Error<Unit>>(handleWeightDialogsQuestionUseCase(accepted = true))
        verifyLastTimeAskedForWeightDialogsCaching()
    }

    @Test
    fun `Check if repository return resource success, resource success is returned and weight dialogs are cached`() = runTest {
        mockData()
        assertIs<Resource.Success<Unit>>(handleWeightDialogsQuestionUseCase(accepted = true))
        coVerify(exactly = 1) {
            cachedValuesProvider.updateWeightDialogs(any())
        }
    }

    private fun verifyLastTimeAskedForWeightDialogsCaching() {
        coVerify(exactly = 1) {
            cachedValuesProvider.updateLocalLastTimeAskedForWeightDialogs(
                date = mockedDate
            )
        }
    }

    private fun mockData(
        responseResource: Resource<WeightDialogs> = Resource.Success(data = mockWeightDialogs())
    ) {
        mockDateString(mockedDate)
        coEvery { cachedValuesProvider.updateLocalLastTimeAskedForWeightDialogs(mockedDate) } returns Unit
        coEvery { cachedValuesProvider.updateWeightDialogs(any()) } returns Unit
        coEvery { weightRepository.handleWeightDialogsQuestion(any()) } returns responseResource
    }

    private fun mockWeightDialogs() = WeightDialogs(
        accepted = true,
        lastTimeAsked = mockedDate2,
        askedCount = 1
    )
}