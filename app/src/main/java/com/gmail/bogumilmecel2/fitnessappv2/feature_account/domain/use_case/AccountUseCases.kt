package com.gmail.bogumilmecel2.fitnessappv2.feature_account.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product.CreatePieChartDataUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case.SaveAskForWeightDailyUseCase

data class AccountUseCases(
    val deleteTokenUseCase: DeleteTokenUseCase,
    val createPieChartDataUseCase: CreatePieChartDataUseCase,
    val saveAskForWeightDailyUseCase: SaveAskForWeightDailyUseCase
)
