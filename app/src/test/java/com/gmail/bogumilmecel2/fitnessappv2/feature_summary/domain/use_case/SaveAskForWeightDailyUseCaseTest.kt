package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseMockkTest
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository.WeightRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SaveAskForWeightDailyUseCaseTest : BaseMockkTest() {

    private val weightRepository = mockk<WeightRepository>()
    private val saveAskForWeightDailyUseCase = SaveAskForWeightDailyUseCase(
        weightRepository = weightRepository
    )

    @Test
    fun `Check if repository returns resource error, resource error is returned`() = runTest {
        mockData(repositoryResource = Resource.Error())
        callTestedMethod().assertIsError()
        verifyAskForWeightDailyCaching(exactly = 0)
    }

    @Test
    fun `Check if repository returns resource success, resource success is returned and accepted is saved locally`() = runTest {
        val accepted = false
        mockData()
        callTestedMethod(accepted).assertIsSuccess()
        verifyAskForWeightDailyCaching(
            exactly = 1,
            accepted = accepted
        )
    }

    private fun mockData(repositoryResource: Resource<Unit> = Resource.Success(Unit)) {
        coEvery { weightRepository.handleWeightDialogsQuestion(weightDialogsRequest = any()) } returns repositoryResource
        coEvery { cachedValuesProvider.updateAskForWeightDaily(any()) } returns Unit
    }

    private fun verifyAskForWeightDailyCaching(
        exactly: Int = 1,
        accepted: Boolean = true
    ) {
        coVerify(exactly = exactly) { cachedValuesProvider.updateAskForWeightDaily(accepted = accepted) }
    }

    private suspend fun callTestedMethod(accepted: Boolean = true) = saveAskForWeightDailyUseCase(
        accepted = accepted,
        cachedValuesProvider = cachedValuesProvider
    )
}