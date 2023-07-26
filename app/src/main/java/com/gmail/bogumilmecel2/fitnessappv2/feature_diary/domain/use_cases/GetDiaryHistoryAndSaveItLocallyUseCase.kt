package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class GetDiaryHistoryAndSaveItLocallyUseCase(private val diaryRepository: DiaryRepository) {
    suspend operator fun invoke(): Resource<Unit> {
        val productDiaryHistory = diaryRepository.getProductDiaryHistory().data ?: return Resource.Error()
        return diaryRepository.saveProductDiaryHistoryLocally(productDiaryHistoryItems = productDiaryHistory)
    }
}