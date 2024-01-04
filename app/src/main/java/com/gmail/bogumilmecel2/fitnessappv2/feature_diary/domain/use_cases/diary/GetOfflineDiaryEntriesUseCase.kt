package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository
import kotlinx.datetime.LocalDate

class GetOfflineDiaryEntriesUseCase(private val offlineDiaryRepository: OfflineDiaryRepository) {
    suspend operator fun invoke(date: LocalDate): List<DiaryItem> {
        return buildList {
            addAll(offlineDiaryRepository.getProductDiaryEntries(date = date).data ?: emptyList())
            addAll(offlineDiaryRepository.getRecipeDiaryEntries(date = date).data ?: emptyList())
        }
    }
}