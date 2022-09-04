package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.search

import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class SearchForProductWithBarcode(
    private val diaryRepository: DiaryRepository
) {

    suspend operator fun invoke(barcode:String):Resource<ProductWithId>{
        return diaryRepository.searchForProductWithBarcode(barcode)
    }
}