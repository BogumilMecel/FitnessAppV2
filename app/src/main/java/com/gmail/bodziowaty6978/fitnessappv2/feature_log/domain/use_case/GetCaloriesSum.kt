package com.gmail.bodziowaty6978.fitnessappv2.feature_log.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class GetCaloriesSum(
    private val diaryRepository: DiaryRepository,
) {
    suspend operator fun invoke(
        date: String
    ): Resource<Int> {
        return diaryRepository.getCaloriesSum(date = date)
    }
}