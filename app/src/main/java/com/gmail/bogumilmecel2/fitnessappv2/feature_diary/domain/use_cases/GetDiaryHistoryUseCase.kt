package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Constants
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class GetDiaryHistoryUseCase(private val diaryRepository: DiaryRepository) {
    suspend operator fun invoke(
        page: Int,
        searchText: String?
    ): Resource<List<ProductDiaryEntry>> {
        return diaryRepository.getOfflineDiaryEntries(
            limit = Constants.DEFAULT_OFFLINE_PAGE_SIZE,
            offset = (Constants.DEFAULT_OFFLINE_PAGE_SIZE * (page - 1)),
            searchText = searchText.orEmpty()
        )
    }
}