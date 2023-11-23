package com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetProductDiaryAndSaveOfflineUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetProductsAndSaveOfflineUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetRecipeDiaryAndSaveOfflineUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetRecipesAndSaveOfflineUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.use_cases.AuthenticateUserUseCase

data class LoadingUseCases(
    val authenticateUserUseCase: AuthenticateUserUseCase,
    val getProductsAndSaveOfflineUseCase: GetProductsAndSaveOfflineUseCase,
    val getRecipesAndSaveOfflineUseCase: GetRecipesAndSaveOfflineUseCase,
    val getProductDiaryAndSaveOfflineUseCase: GetProductDiaryAndSaveOfflineUseCase,
    val getRecipeDiaryAndSaveOfflineUseCase: GetRecipeDiaryAndSaveOfflineUseCase
)