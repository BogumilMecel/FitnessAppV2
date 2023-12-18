package com.gmail.bodziowaty6978.fitnessappv2.features.feature_diary.domain.use_cases

import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.features.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.features.feature_diary.domain.repository.DiaryRepository

class GetHistory(
    private val repository: DiaryRepository
) {
    suspend operator fun invoke():Resource<List<Product>>{
        return repository.getLocalProductHistory()
    }
}