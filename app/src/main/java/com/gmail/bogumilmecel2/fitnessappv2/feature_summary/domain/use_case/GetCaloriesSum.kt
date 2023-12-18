package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class GetCaloriesSum(private val diaryRepository: DiaryRepository) {
    suspend operator fun invoke(date: String): Int {
        val nutritionValues = diaryRepository.getDiaryEntriesNutritionValues(date).data ?: return 0
        return nutritionValues.sumOf { it.calories }
    }
}