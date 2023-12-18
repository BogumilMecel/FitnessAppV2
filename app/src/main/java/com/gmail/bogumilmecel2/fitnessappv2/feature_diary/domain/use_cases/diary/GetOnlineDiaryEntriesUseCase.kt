package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository

class GetOnlineDiaryEntriesUseCase(
    private val diaryRepository: DiaryRepository,
    private val offlineDiaryRepository: OfflineDiaryRepository
) {
    suspend operator fun invoke(date: String): Resource<Unit> {
        val diaryEntries = diaryRepository.getDiaryEntries(date = date).data ?: return Resource.Error()
        offlineDiaryRepository.deleteProductDiaryEntries(
            date = date,
            productDiaryEntriesIds = diaryEntries.productDiaryEntries.map { it.id }
        )
        offlineDiaryRepository.deleteRecipeDiaryEntries(
            date = date,
            recipeDiaryEntriesIds = diaryEntries.recipeDiaryEntries.map { it.id }
        )

        offlineDiaryRepository.insertRecipeDiaryEntries(diaryEntries.recipeDiaryEntries)
        offlineDiaryRepository.insertProductDiaryEntries(diaryEntries.productDiaryEntries)

        return Resource.Success(Unit)
    }
}