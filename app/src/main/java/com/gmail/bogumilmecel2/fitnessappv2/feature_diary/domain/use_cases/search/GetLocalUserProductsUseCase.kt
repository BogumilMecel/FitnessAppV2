package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.search

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ApiConstants
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.CalculateSkipUseCase

class GetLocalUserProductsUseCase (
    private val cachedValuesProvider: CachedValuesProvider,
    private val diaryRepository: DiaryRepository,
    private val calculateSkipUseCase: CalculateSkipUseCase
) {
    suspend operator fun invoke(
        searchText: String?,
        page: Int,
    ) = diaryRepository.getLocalUserProducts(
        userId = cachedValuesProvider.getUserId(),
        searchText = searchText,
        limit = ApiConstants.DEFAULT_OFFLINE_PAGE_SIZE,
        skip = calculateSkipUseCase(
            page = page,
            sizePerPage = ApiConstants.DEFAULT_OFFLINE_PAGE_SIZE
        )
    )
}