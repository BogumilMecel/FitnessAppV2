package com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.util.BottomBarScreen
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetUserDiaryAndSaveItLocallyUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetUserDiaryEntriesExperimentalUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.repository.LoadingRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.test.assertEquals

class AuthenticateUserUseCaseTest : BaseTest() {

    companion object {
        @JvmStatic
        private fun createCachedUserParams() = buildList {
            addAll(
                createUserParams().map {
                    Arguments.of(
                        it,
                        ConnectException()
                    )
                }
            )

            addAll(
                createUserParams().map {
                    Arguments.of(
                        it,
                        SocketTimeoutException()
                    )
                }
            )

            addAll(
                createUserParams().map {
                    Arguments.of(
                        it,
                        UnknownHostException()
                    )
                }
            )
        }

        @JvmStatic
        private fun createUserParams() = listOf(
            null,
            User(
                nutritionValues = null,
                userInformation = null
            ),
            User(
                nutritionValues = NutritionValues(),
                userInformation = null
            ),
            User(
                nutritionValues = null,
                userInformation = UserInformation()
            ),
            User(
                nutritionValues = NutritionValues(),
                userInformation = UserInformation()
            ),
        )
    }

    private val loadingRepository = mockk<LoadingRepository>()
    private val tokenRepository = mockk<TokenRepository>()
    private val getUserDiaryAndSaveItLocallyUseCase =
        mockkClass(GetUserDiaryAndSaveItLocallyUseCase::class)
    private val getUserDiaryEntriesExperimentalUseCase =
        mockkClass(GetUserDiaryEntriesExperimentalUseCase::class)
    private val authenticateUserUseCase = AuthenticateUserUseCase(
        cachedValuesProvider = cachedValuesProvider,
        loadingRepository = loadingRepository,
        tokenRepository = tokenRepository,
        getUserDiaryAndSaveItLocallyUseCase = getUserDiaryAndSaveItLocallyUseCase,
        getUserDiaryEntriesExperimentalUseCase = getUserDiaryEntriesExperimentalUseCase
    )

    @Test
    fun `Check if token repository returns error result navigate to login screen is returned`() =
        runTest {
            mockData(tokenResource = Resource.Error())
            assertEquals(
                expected = AuthenticateUserUseCase.Result.NavigateToLogin,
                actual = callTestedMethod()
            )
        }

    @ParameterizedTest
    @MethodSource("createCachedUserParams")
    fun `Check if offline mode works correctly if authentication returns resource error`(
        user: User?,
        exception: Exception,
    ) = runTest {
        mockData(authenticateResource = Resource.ComplexError(exception))
        coEvery { cachedValuesProvider.getUser() } returns user

        val offlineModeExceptions = listOf(
            SocketTimeoutException::class,
            ConnectException::class
        )

        assertEquals(
            expected = if (user?.nutritionValues != null && user.userInformation != null && exception::class in offlineModeExceptions) {
                AuthenticateUserUseCase.Result.NavigateToMainScreen(BottomBarScreen.Summary)
            } else {
                AuthenticateUserUseCase.Result.NavigateToLogin
            },
            actual = callTestedMethod()
        )
    }

    @ParameterizedTest
    @MethodSource("createUserParams")
    fun `Check if authentication works correctly is authentication return resource success`(user: User?) =
        runTest {
            mockData(authenticateResource = Resource.Success(user))
            user?.let {
                coEvery { cachedValuesProvider.saveUser(user) } returns Unit
            }
            coEvery { getUserDiaryAndSaveItLocallyUseCase() } returns Resource.Success(Unit)
            coEvery { getUserDiaryEntriesExperimentalUseCase() } returns Resource.Success(Unit)

            assertEquals(
                expected = if (user == null) {
                    AuthenticateUserUseCase.Result.NavigateToLogin
                } else if (user.nutritionValues == null || user.userInformation == null) {
                    AuthenticateUserUseCase.Result.NavigateToIntroduction
                } else {
                    AuthenticateUserUseCase.Result.NavigateToMainScreen(BottomBarScreen.Summary)
                },
                actual = callTestedMethod()
            )

            user?.let {
                coVerify(exactly = 1) {
                    cachedValuesProvider.saveUser(user)
                }
            }

            coVerify(exactly = if (user?.nutritionValues != null && user.userInformation != null) 1 else 0) {
                getUserDiaryAndSaveItLocallyUseCase()
                getUserDiaryEntriesExperimentalUseCase()
            }
        }

    private fun mockData(
        tokenResource: Resource<String> = Resource.Success(MockConstants.Auth.TOKEN),
        authenticateResource: Resource<User?> = Resource.Error(),

    ) {
        coEvery { tokenRepository.getToken() } returns tokenResource
        coEvery { loadingRepository.authenticateUser() } returns authenticateResource
    }

    private suspend fun callTestedMethod() = authenticateUserUseCase()
}