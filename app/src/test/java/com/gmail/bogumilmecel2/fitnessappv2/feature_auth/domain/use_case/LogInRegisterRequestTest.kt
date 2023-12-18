package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.repository.AuthRepository
import io.mockk.mockk

internal class LogInRegisterRequestTest: BaseTest() {

    private val authRepository = mockk<AuthRepository>()
    private val tokenRepository = mockk<TokenRepository>()
    private val logInUserUseCase = LogInUserUseCase(
        authRepository = authRepository,
        resourceProvider = resourceProvider,
        tokenRepository = tokenRepository
    )

//    @Before
//    fun setUp() = runTest{
//        closeable = MockitoAnnotations.openMocks(this)
//
//        authRepository = Mockito.mock(AuthRepository::class.java)
//        Mockito.`when`(authRepository.logInUser(anyString(), anyString())).thenReturn(CustomResult.Success)
//        logInUser = LogInUser(
//            repository = authRepository,
//            resourceProvider = ResourceProvider(context = RuntimeEnvironment.getApplication())
//        )
//    }
//
//    @Test
//    fun `log in with blank email, returned error result`() = runTest {
//        val result = logInUser(
//            email = "",
//            password = AuthConstants.ValidPassword
//        )
//
//        assertTrue(result is CustomResult.Error)
//    }
//
//    @Test
//    fun `log in with blank password, returned error result`() = runTest {
//        val result = logInUser(
//            email = AuthConstants.ValidEmail,
//            password = ""
//        )
//
//        assertTrue(result is CustomResult.Error)
//    }
//
//    @Test
//    fun `log in with invalid email, error result`() = runTest {
//        val result = logInUser(
//            email = AuthConstants.InvalidEmail,
//            password = AuthConstants.ValidPassword
//        )
//
//        assertTrue(result is CustomResult.Error)
//    }
//
//    @Test
//    fun `log in user, returned success result`() = runTest {
//        val result = logInUser(
//            email = AuthConstants.ValidEmail,
//            password = AuthConstants.ValidPassword
//        )
//
//        assertTrue(result is CustomResult.Success)
//    }
}