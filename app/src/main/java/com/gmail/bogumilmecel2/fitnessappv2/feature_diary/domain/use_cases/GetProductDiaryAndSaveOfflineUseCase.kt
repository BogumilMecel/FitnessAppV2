package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository

class GetProductDiaryAndSaveOfflineUseCase(
    private val diaryRepository: DiaryRepository,
    private val cachedValuesProvider: CachedValuesProvider,
    private val offlineDiaryRepository: OfflineDiaryRepository
) {
    suspend operator fun invoke(): Resource<Unit> {
        if (cachedValuesProvider.getOfflineMode().isOffline()) return Resource.Error()

        val latestOfflineProductDiaryEntry = offlineDiaryRepository.getProductDiaryEntries(
            limit = 1
        ).data ?: return Resource.Error()

        val userProductDiaryEntries = diaryRepository.getProductDiaryEntries(
            latestTimestamp = latestOfflineProductDiaryEntry.firstOrNull()?.utcTimestamp
        ).data ?: return Resource.Error()

        return offlineDiaryRepository.insertProductDiaryEntries(userProductDiaryEntries)
    }
}