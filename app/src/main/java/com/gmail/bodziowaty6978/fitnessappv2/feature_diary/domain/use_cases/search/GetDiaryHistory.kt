package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.search

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.GetToken
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository


class GetDiaryHistory(
    private val repository: DiaryRepository,
    private val getToken: GetToken
) {
    suspend operator fun invoke():Resource<List<Product>>{
        val token = getToken()

        return token?.let {
            repository.getProductHistory(it)
        } ?: kotlin.run {
            Resource.Success(data = emptyList())
        }
    }
}