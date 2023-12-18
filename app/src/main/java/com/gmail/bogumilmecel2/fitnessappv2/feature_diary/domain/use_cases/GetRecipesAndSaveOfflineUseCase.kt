package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository

class GetRecipesAndSaveOfflineUseCase(
    private val cachedValuesProvider: CachedValuesProvider,
    private val offlineDiaryRepository: OfflineDiaryRepository,
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(): Resource<Unit> {
        if (cachedValuesProvider.getOfflineMode().isOffline()) return Resource.Error()

        val latestOfflineRecipe = offlineDiaryRepository.getRecipes(
            userId = cachedValuesProvider.getUserId(),
            limit = 1,
        ).data ?: return Resource.Error()

        val userRecipes = diaryRepository.getUserRecipes(
            latestTimestamp = latestOfflineRecipe.firstOrNull()?.utcTimestamp
        ).data ?: return Resource.Error()

        return offlineDiaryRepository.insertRecipes(userRecipes)
    }
}