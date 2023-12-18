package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Constants
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository

class GetDiaryHistoryUseCase(private val offlineDiaryRepository: OfflineDiaryRepository) {
    suspend operator fun invoke(
        page: Int,
        searchText: String?
    ): Resource<List<ProductDiaryEntry>> {
        return offlineDiaryRepository.getProductDiaryEntries(
            limit = Constants.DEFAULT_OFFLINE_PAGE_SIZE,
            skip = (Constants.DEFAULT_OFFLINE_PAGE_SIZE * (page - 1)),
            searchText = searchText
        )
    }
}