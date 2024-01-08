package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.WeightDialogsQuestion

class HandleWeightDialogsQuestionUseCase(
    private val cachedValuesProvider: CachedValuesProvider,
    private val saveAskForWeightDaily: SaveAskForWeightDailyUseCase
) {
    suspend operator fun invoke(accepted: Boolean?): Resource<Unit> {
        if (accepted == null) {
            val weightDialogsQuestion = cachedValuesProvider.getLocalWeightDialogsQuestion()
            cachedValuesProvider.updateLocalWeightDialogsQuestion(
                weightDialogsQuestion = WeightDialogsQuestion(
                    askedCount = weightDialogsQuestion?.askedCount?.plus(1) ?: 1,
                    lastTimeAsked = CustomDateUtils.getDate()
                )
            )
            return Resource.Error()
        }

        return saveAskForWeightDaily(
            accepted = accepted,
            cachedValuesProvider = cachedValuesProvider
        )
    }
}