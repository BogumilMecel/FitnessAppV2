package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.OfflineDiaryRepository

class GetCaloriesSum(private val offlineDiaryRepository: OfflineDiaryRepository) {
    suspend operator fun invoke(date: String): Int {
        val nutritionValues = offlineDiaryRepository.getDiaryEntriesNutritionValues(date).data ?: return 0
        return nutritionValues.sumOf { it.calories }
    }
}