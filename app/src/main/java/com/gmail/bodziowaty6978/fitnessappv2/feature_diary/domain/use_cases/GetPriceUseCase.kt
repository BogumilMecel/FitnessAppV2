package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.GetUserCurrencyUseCase
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.ProductPrice
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.repository.DiaryRepository

class GetPriceUseCase(
    private val diaryRepository: DiaryRepository,
    private val getUserCurrency: GetUserCurrencyUseCase
) {
    suspend operator fun invoke(productId: String): Resource<ProductPrice?> {
        return diaryRepository.getPrice(
            productId = productId,
            currency = getUserCurrency()
        )
    }
}