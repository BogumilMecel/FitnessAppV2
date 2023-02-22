package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.formatToString
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Meal
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class GetDiaryEntries(
    private val diaryRepository: DiaryRepository,
    private val sortDiaryEntries: SortDiaryEntries
) {
    suspend operator fun invoke(
        timestamp: Long,
        mealNames: List<String>
    ): Resource<List<Meal>> {
        val resource = diaryRepository.getDiaryEntries(
            date = Date(timestamp).formatToString()
        )
        return if (resource is Resource.Error) {
            Resource.Error(uiText = resource.uiText)
        } else {
            withContext(Dispatchers.Default) {
                Resource.Success(
                    data = sortDiaryEntries(
                        entries = resource.data ?: emptyList(),
                        mealNames = mealNames
                    )
                )
            }
        }
    }
}