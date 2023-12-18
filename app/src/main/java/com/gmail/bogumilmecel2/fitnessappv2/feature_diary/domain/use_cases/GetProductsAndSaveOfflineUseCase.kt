package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository

class GetProductsAndSaveOfflineUseCase(
    private val diaryRepository: DiaryRepository,
    private val offlineDiaryRepository: OfflineDiaryRepository,
    private val cachedValuesProvider: CachedValuesProvider
) {
    suspend operator fun invoke(): Resource<Unit> {
        if (cachedValuesProvider.getOfflineMode().isOffline()) return Resource.Error()

        val latestOfflineProduct = offlineDiaryRepository.getProducts(
            userId = cachedValuesProvider.getUserId(),
            limit = 1,
        ).data ?: return Resource.Error()

        val userProducts = diaryRepository.getUserProducts(
            latestTimestamp = latestOfflineProduct.firstOrNull()?.utcTimestamp
        ).data ?: return Resource.Error()

        return offlineDiaryRepository.insertProducts(userProducts)
    }
}