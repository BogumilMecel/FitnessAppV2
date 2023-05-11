package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
internal class ResetPasswordWithEmailTest{
    @Mock
    private lateinit var authRepository: AuthRepository
    private lateinit var resetPasswordWithEmail: ResetPasswordWithEmail
    private lateinit var closeable: AutoCloseable

//    @Before
//    fun setUp() = runTest{
//        closeable = MockitoAnnotations.openMocks(this)
//
//        authRepository = Mockito.mock(AuthRepository::class.java)
//        Mockito.`when`(authRepository.sendPasswordResetEmail(anyString())).thenReturn(CustomResult.Success)
//        resetPasswordWithEmail = ResetPasswordWithEmail(
//            repository = authRepository,
//            resourceProvider = ResourceProvider(context = RuntimeEnvironment.getApplication())
//        )
//    }
//
//    @Test
//    fun `reset password with blank email, returned error result`() = runBlocking {
//        val result = resetPasswordWithEmail(
//            email = ""
//        )
//        assertTrue(result is CustomResult.Error)
//    }
//
//    @Test
//    fun `reset password with email, returned success result`() = runBlocking {
//        val result = resetPasswordWithEmail(
//            email = AuthConstants.ValidEmail
//        )
//
//        assertTrue(result is CustomResult.Success )
//    }
//
//    @Test
//    fun `reset password with invalid email, error result`() = runTest {
//        val result = resetPasswordWithEmail(
//            email = AuthConstants.InvalidEmail,
//        )
//
//        assertTrue(result is CustomResult.Error)
//    }

    @After
    fun after(){
        closeable.close()
    }
}