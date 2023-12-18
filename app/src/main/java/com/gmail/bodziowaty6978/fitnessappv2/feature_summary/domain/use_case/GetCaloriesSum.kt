package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class GetCaloriesSum(
    private val diaryRepository: DiaryRepository,
) {
    suspend operator fun invoke(
        date: String
    ): Int {
        return diaryRepository.getCaloriesSum(date = date).data ?: 0
    }
}