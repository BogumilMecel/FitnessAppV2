package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource

class CheckIfShouldShowWeightPickerUseCase(private val cachedValuesProvider: CachedValuesProvider) {
    suspend operator fun invoke(): Resource<Unit> {
        val lastTimeWeightDialogsShown = cachedValuesProvider.getLocalLastTimeShowedWeightPicker()
        val currentDate = CustomDateUtils.getDate()

        if (lastTimeWeightDialogsShown == currentDate) return Resource.Error()

        val user = cachedValuesProvider.getUser() ?: return Resource.Error()

        if (user.askForWeightDaily != true) return Resource.Error()

        if (user.latestWeightEntry?.date == CustomDateUtils.getDate()) return Resource.Error()

        cachedValuesProvider.setLocalLastTimeShowedWeightPicker(date = currentDate)

        return Resource.Success(Unit)
    }
}