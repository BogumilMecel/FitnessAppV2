package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository
import kotlinx.datetime.LocalDate

class GetOnlineDiaryEntriesUseCase(
    private val diaryRepository: DiaryRepository,
    private val offlineDiaryRepository: OfflineDiaryRepository
) {
    suspend operator fun invoke(date: LocalDate): Resource<Unit> {
        val diaryEntries = diaryRepository.getDiaryEntries(date = date).data ?: return Resource.Error()
        offlineDiaryRepository.deleteProductDiaryEntries(date = date)
        offlineDiaryRepository.deleteRecipeDiaryEntries(date = date)
        offlineDiaryRepository.insertRecipeDiaryEntries(diaryEntries.recipeDiaryEntries)
        offlineDiaryRepository.insertProductDiaryEntries(diaryEntries.productDiaryEntries)

        return Resource.Success(Unit)
    }
}