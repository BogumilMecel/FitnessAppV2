package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DeleteDiaryEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository

class DeleteDiaryEntryUseCase(
    private val diaryRepository: DiaryRepository,
    private val offlineDiaryRepository: OfflineDiaryRepository
) {
    suspend operator fun invoke(diaryItem: DiaryItem): Resource<Unit> = with(diaryItem) {
        id?.let { id ->
            val onlineDeleteDiaryEntryRequest = diaryRepository.deleteDiaryEntry(
                deleteDiaryEntryRequest = DeleteDiaryEntryRequest(
                    diaryEntryId = id,
                    diaryEntryType = diaryItem.getDiaryEntryType()
                ),
            )

            return when(onlineDeleteDiaryEntryRequest) {
                is Resource.Error -> {
                    Resource.Error()
                }

                is Resource.Success -> {
                    when(diaryItem) {
                        is ProductDiaryEntry -> offlineDiaryRepository.deleteProductDiaryEntry(id)
                        is RecipeDiaryEntry -> offlineDiaryRepository.deleteRecipeDiaryEntry(id)
                        else -> Resource.Error()
                    }
                }
            }
        }

        return Resource.Error()
    }
}