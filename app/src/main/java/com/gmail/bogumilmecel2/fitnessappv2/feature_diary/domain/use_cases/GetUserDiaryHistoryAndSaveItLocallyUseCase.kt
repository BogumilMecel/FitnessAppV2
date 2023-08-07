package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class GetUserDiaryHistoryAndSaveItLocallyUseCase(private val diaryRepository: DiaryRepository) {
    suspend operator fun invoke(page: Int = 1): Resource<Unit> {
        val diaryHistory =
            diaryRepository.getOnlineDiaryHistory(
                page = page,
                searchText = null
            ).data ?: return Resource.Error()

        if (diaryHistory.isNotEmpty()) {
            diaryRepository.insertLocalHistoryItems(productDiaryHistoryItems = diaryHistory)
        }

        return Resource.Success(Unit)
    }
}