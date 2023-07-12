package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseMockkTest
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightDialogs
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository.WeightRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertIs

class CheckIfShouldAskForWeightDialogsUseCaseTest : BaseMockkTest() {

    private val weightRepository = mockk<WeightRepository>()
    private val checkIfShouldAskForWeightDialogsUseCase =
        CheckIfShouldAskForWeightDialogsUseCase(weightRepository = weightRepository)
    private val mockedDate = "2023-12-12"
    private val mockedDate2 = "2022-12-12"

    @Test
    fun `check if user has already accepted weight dialogs, resource error is returned`() =
        runTest {
            mockData(weightDialogs = mockWeightDialogs(accepted = true))
            assertIs<Resource.Error<Unit>>(callTestedMethod())
        }

    @Test
    fun `check if user has already seen weight dialogs offline, resource error is returned`() =
        runTest {
            mockData(localLastTimeAskedForWeightDialogsDate = mockedDate)
            mockDateString(mockedDate)
            assertIs<Resource.Error<Unit>>(callTestedMethod())
        }

    @Test
    fun `check if user has already seen weight dialogs online, resource error is returned and online date is saved offline`() =
        runTest {
            coEvery { cachedValuesProvider.updateLocalLastTimeAskedForWeightDialogs(mockedDate) } returns Unit
            mockData(weightDialogs = mockWeightDialogs(lastTimeAsked = mockedDate))
            mockDateString(mockedDate)
            assertIs<Resource.Error<Unit>>(callTestedMethod())
            coVerify(exactly = 1) {
                cachedValuesProvider.updateLocalLastTimeAskedForWeightDialogs(mockedDate)
            }
        }

    @Test
    fun `check if user has already been asked 3 times, resource error is returned`() =
        runTest {
            mockData(weightDialogs = mockWeightDialogs(askedCount = 4))
            assertIs<Resource.Error<Unit>>(callTestedMethod())
        }

    @Test
    fun `check if repository returns resource error, resource error is returned`() =
        runTest {
            mockData(repositoryResponse = Resource.Error())
            assertIs<Resource.Error<Unit>>(callTestedMethod())
        }

    @Test
    fun `check if repository returns resource success and everything else is correct, resource success is returned`() =
        runTest {
            mockData()
            mockDateString(value = mockedDate)
            assertIs<Resource.Success<Unit>>(callTestedMethod())
        }

    private fun mockData(
        weightDialogs: WeightDialogs = mockWeightDialogs(),
        repositoryResponse: Resource<Unit> = Resource.Success(Unit),
        localLastTimeAskedForWeightDialogsDate: String = mockedDate2
    ) {
        coEvery { cachedValuesProvider.getLocalLastTimeAskedForWeightDialogs() } returns localLastTimeAskedForWeightDialogsDate
        coEvery { cachedValuesProvider.getUser() } returns User(weightDialogs = weightDialogs)
        coEvery { weightRepository.checkIfShouldAskForWeightDialogs() } returns repositoryResponse
    }

    private suspend fun callTestedMethod() =
        checkIfShouldAskForWeightDialogsUseCase(cachedValuesProvider)

    private fun mockWeightDialogs(
        accepted: Boolean? = false,
        lastTimeAsked: String = mockedDate2,
        askedCount: Int = 3
    ) = WeightDialogs(
        accepted = accepted,
        lastTimeAsked = lastTimeAsked,
        askedCount = askedCount
    )
}