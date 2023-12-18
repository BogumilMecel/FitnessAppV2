package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Price
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class AddNewPrice(
    private val diaryRepository: DiaryRepository
) {

    suspend operator fun invoke(
        price: Price,
        productId:String
    ):Resource<Price>{
        return diaryRepository.addNewPrice(
            price = price,
            productId = productId
        )
    }
}