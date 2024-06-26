package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Constants
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CalculateSkipUseCase

class GetOfflineUserRecipesUseCase(
    private val cachedValuesProvider: CachedValuesProvider,
    private val offlineDiaryRepository: OfflineDiaryRepository,
    private val calculateSkipUseCase: CalculateSkipUseCase
) {
    suspend operator fun invoke(
        searchText: String?,
        page: Int
    ) = offlineDiaryRepository.getRecipes(
        userId = cachedValuesProvider.getUserId(),
        searchText = searchText,
        limit = Constants.DEFAULT_OFFLINE_PAGE_SIZE,
        skip = calculateSkipUseCase(
            page = page,
            sizePerPage = Constants.DEFAULT_OFFLINE_PAGE_SIZE
        )
    )
}