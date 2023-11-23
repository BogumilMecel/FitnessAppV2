package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository

class GetRecipeDiaryAndSaveOfflineUseCase(
    private val cachedValuesProvider: CachedValuesProvider,
    private val offlineDiaryRepository: OfflineDiaryRepository,
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(): Resource<Unit> {
        if (cachedValuesProvider.getOfflineMode().isOffline()) return Resource.Error()

        val latestOfflineRecipeDiaryEntry = offlineDiaryRepository.getRecipeDiaryEntries(
            limit = 1,
        ).data ?: return Resource.Error()

        val userRecipeDiaryEntries = diaryRepository.getRecipeDiaryEntries(
            latestTimestamp = latestOfflineRecipeDiaryEntry.firstOrNull()?.utcTimestamp
        ).data ?: return Resource.Error()

        return offlineDiaryRepository.insertRecipeDiaryEntries(userRecipeDiaryEntries)
    }
}