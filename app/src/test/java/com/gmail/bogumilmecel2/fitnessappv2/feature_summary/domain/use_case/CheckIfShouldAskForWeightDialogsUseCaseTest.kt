package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseMockkTest
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository.WeightRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertIs

class CheckIfShouldAskForWeightDialogsUseCaseTest: BaseMockkTest() {

    private val weightRepository = mockk<WeightRepository>()
    private val checkIfShouldAskForWeightDialogsUseCase =
        CheckIfShouldAskForWeightDialogsUseCase(weightRepository = weightRepository)
    private val cachedValuesProvider = mockk<CachedValuesProvider>()
    private val mockedDate = "2023-12-12"
    private val mockedDate2 = "2022-12-12"

    @Test
    fun `check if get user returns null, resource error is returned`() =
        runTest {
            mockData(user = null)
            assertIs<Resource.Error<Unit>>(callTestedMethod())
        }

    @Test
    fun `check if user has already been asked for weight dialogs, resource error is returned`() =
        runTest {
            mockData(user = User(weightDialogsAccepted = true))
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
            mockData(User(lastTimeAskedAboutWeightDialogs = mockedDate))
            mockDateString(value = mockedDate2)
            assertIs<Resource.Success<Unit>>(callTestedMethod())
        }

    @Test
    fun `check if user has been already asked today, resource error is returned`() =
        runTest {
            mockData(User(lastTimeAskedAboutWeightDialogs = mockedDate))
            mockDateString(mockedDate)
            assertIs<Resource.Error<Unit>>(callTestedMethod())
        }

    @Test
    fun `check if user has not been already asked today, resource error is returned`() =
        runTest {
            mockData(User(lastTimeAskedAboutWeightDialogs = mockedDate))
            mockDateString(mockedDate)
            assertIs<Resource.Error<Unit>>(callTestedMethod())
        }

    private fun mockData(
        user: User? = User(weightDialogsAccepted = false),
        repositoryResponse: Resource<Unit> = Resource.Success(Unit)
    ) {
        coEvery { cachedValuesProvider.getUser() } returns user
        coEvery { weightRepository.checkIfShouldAskForWeightDialogs() } returns repositoryResponse
    }

    private suspend fun callTestedMethod() =
        checkIfShouldAskForWeightDialogsUseCase(cachedValuesProvider)
}