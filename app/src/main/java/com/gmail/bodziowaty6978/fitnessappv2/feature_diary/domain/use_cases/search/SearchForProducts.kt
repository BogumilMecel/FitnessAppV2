package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.search

import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class SearchForProducts(
    private val diaryRepository: DiaryRepository,
    private val getDiaryHistory: GetDiaryHistory
) {

    suspend operator fun invoke(searchText:String):Resource<List<Product>>{
        if (searchText.isBlank()){
            return getDiaryHistory()
        }
        return diaryRepository.searchForProducts(searchText)
    }
}