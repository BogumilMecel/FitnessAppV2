package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import android.util.Log
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.TAG
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class GetUserDiaryEntriesExperimentalUseCase(private val diaryRepository: DiaryRepository) {
    suspend operator fun invoke(): Resource<Unit> {
        val diaryEntriesCount = diaryRepository.getDiaryEntriesCount().data ?: return Resource.Error()

        if (diaryEntriesCount <= 0) {
            val diaryEntriesResponse = diaryRepository.getDiaryEntriesComplete()
            Log.e(TAG, diaryEntriesResponse.toString())
        } else {
            val diaryEntriesResponse = diaryRepository.getDiaryEntriesExperimental()
        }

        return Resource.Success(Unit)
    }
}