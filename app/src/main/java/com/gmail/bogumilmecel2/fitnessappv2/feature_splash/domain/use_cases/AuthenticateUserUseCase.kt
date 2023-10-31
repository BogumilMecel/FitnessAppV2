package com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.util.BottomBarScreen
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetUserDiaryAndSaveItLocallyUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetUserDiaryEntriesExperimentalUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.repository.LoadingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import java.net.ConnectException
import java.net.SocketTimeoutException

class AuthenticateUserUseCase(
    private val cachedValuesProvider: CachedValuesProvider,
    private val loadingRepository: LoadingRepository,
    private val tokenRepository: TokenRepository,
    private val getUserDiaryAndSaveItLocallyUseCase: GetUserDiaryAndSaveItLocallyUseCase,
    private val getUserDiaryEntriesExperimentalUseCase: GetUserDiaryEntriesExperimentalUseCase
) {
    suspend operator fun invoke(): Result {
        tokenRepository.getToken().data ?: return Result.NavigateToLogin

        val resource = loadingRepository.authenticateUser()

        val offlineModeExceptions = listOf(
            SocketTimeoutException::class,
            ConnectException::class
        )

        val cachedUser = cachedValuesProvider.getUser()

        when (resource) {
            is Resource.Error -> {
                return if (resource is Resource.ComplexError
                    && (resource.exception::class in offlineModeExceptions)
                    && cachedUser != null
                    && cachedUser.userInformation != null
                    && cachedUser.nutritionValues != null
                ) {
                    Result.NavigateToMainScreen()
                } else {
                    Result.NavigateToLogin
                }
            }

            is Resource.Success -> {
                val user = resource.data ?: return Result.NavigateToLogin

                cachedValuesProvider.saveUser(user = user)

                return if (user.nutritionValues != null && user.userInformation != null) {
                    (Dispatchers.IO) {
                        getUserDiaryAndSaveItLocallyUseCase()
                        getUserDiaryEntriesExperimentalUseCase()
                    }

                    Result.NavigateToMainScreen()
                } else {
                    Result.NavigateToIntroduction
                }
            }
        }
    }

    sealed interface Result {
        data object NavigateToIntroduction : Result
        data object NavigateToLogin : Result
        data class NavigateToMainScreen(
            val bottomBarScreen: BottomBarScreen = BottomBarScreen.Summary
        ) : Result
    }
}