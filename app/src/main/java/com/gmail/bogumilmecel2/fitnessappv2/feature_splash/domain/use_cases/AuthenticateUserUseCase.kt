package com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.util.BottomBarScreen
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.repository.LoadingRepository

class AuthenticateUserUseCase(
    private val cachedValuesProvider: CachedValuesProvider,
    private val loadingRepository: LoadingRepository,
    private val tokenRepository: TokenRepository,
) {
    suspend operator fun invoke(): Result {
        tokenRepository.getToken().data ?: return Result.NavigateToLogin

        if (!cachedValuesProvider.getOfflineMode().isOffline()) {
            val resource = loadingRepository.authenticateUser()

            val user = resource.data ?: return Result.NavigateToLogin

            cachedValuesProvider.saveUser(user = user)

            return if (user.nutritionValues != null && user.userInformation != null) {
                Result.NavigateToMainScreen()
            } else {
                Result.NavigateToIntroduction
            }
        } else {
            val cachedUser = cachedValuesProvider.getUser()

            return if (cachedUser?.userInformation != null && cachedUser.nutritionValues != null) {
                Result.NavigateToMainScreen()
            } else {
                Result.NavigateToLogin
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