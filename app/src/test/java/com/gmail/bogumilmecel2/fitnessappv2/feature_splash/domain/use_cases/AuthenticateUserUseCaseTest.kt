package com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.repository.LoadingRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class AuthenticateUserUseCaseTest : BaseTest() {

    companion object {
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
    private val authenticateUserUseCase = AuthenticateUserUseCase(
        cachedValuesProvider = cachedValuesProvider,
        loadingRepository = loadingRepository,
        tokenRepository = tokenRepository,
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

    @Test
    fun `Check if user is in online mode and authentication returns error navigate to login screen is returned`() =
        runTest {
            mockData(authenticateResource = Resource.Error())
            assertEquals(
                expected = AuthenticateUserUseCase.Result.NavigateToLogin,
                actual = callTestedMethod()
            )
        }

    @ParameterizedTest
    @MethodSource("createUserParams")
    fun `check if correct result is returned in online mode`(user: User?) = runTest {
        mockData(authenticateResource = Resource.Success(user))
        callTestedMethod().run {
            assertEquals(
                actual = this,
                expected = when {
                    user == null -> AuthenticateUserUseCase.Result.NavigateToLogin
                    user.nutritionValues == null && user.userInformation == null -> AuthenticateUserUseCase.Result.NavigateToIntroduction
                    user.nutritionValues == null -> AuthenticateUserUseCase.Result.NavigateToIntroduction
                    user.userInformation == null -> AuthenticateUserUseCase.Result.NavigateToIntroduction
                    else -> AuthenticateUserUseCase.Result.NavigateToMainScreen()
                }
            )
        }
        user?.let {
            coVerify(exactly = 1) { cachedValuesProvider.saveUser(user = it) }
        }
    }

    @ParameterizedTest
    @MethodSource("createUserParams")
    fun `check if correct result is returned in offline mode`(user: User?) = runTest {
        mockData(
            cachedUser = user,
            online = false
        )
        callTestedMethod().run {
            assertEquals(
                actual = this,
                expected = when {
                    user == null -> AuthenticateUserUseCase.Result.NavigateToLogin
                    user.nutritionValues == null && user.userInformation == null -> AuthenticateUserUseCase.Result.NavigateToLogin
                    user.nutritionValues == null -> AuthenticateUserUseCase.Result.NavigateToLogin
                    user.userInformation == null -> AuthenticateUserUseCase.Result.NavigateToLogin
                    else -> AuthenticateUserUseCase.Result.NavigateToMainScreen()
                }
            )
        }
        coVerify(exactly = 1) { cachedValuesProvider.getUser() }
    }

    private fun mockData(
        tokenResource: Resource<String> = Resource.Success(MockConstants.Auth.TOKEN),
        authenticateResource: Resource<User?> = Resource.Error(),
        cachedUser: User? = null,
        online: Boolean = true
    ) {
        coEvery { tokenRepository.getToken() } returns tokenResource
        coEvery { loadingRepository.authenticateUser() } returns authenticateResource
        coEvery { cachedValuesProvider.saveUser(user = any()) } returns Unit
        coEvery { cachedValuesProvider.getUser() } returns cachedUser
        mockOfflineMode(online = online)
    }

    private suspend fun callTestedMethod() = authenticateUserUseCase()
}